/*
 * Copyright 2015 Niclas Hedhman, niclas@hedhman.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sensorsink.pond.rest.rioprime;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.zest.api.entity.EntityBuilder;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.mixin.Initializable;
import org.apache.zest.api.mixin.InitializationException;
import org.apache.zest.api.object.ObjectFactory;
import org.apache.zest.api.unitofwork.NoSuchEntityException;
import org.apache.zest.api.unitofwork.UnitOfWork;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.points.Point;
import org.sensorsink.pond.model.points.PointInfo;
import org.sensorsink.pond.rest.common.RestLink;
import org.sensorsink.pond.rest.rioprime.types.BlockDTO;
import org.sensorsink.pond.rest.rioprime.types.EdgeDTO;
import org.sensorsink.pond.rest.rioprime.types.EntryPointBookmark;
import org.sensorsink.pond.rest.rioprime.types.PointData;
import org.sensorsink.pond.rest.rioprime.types.PointsData;
import org.sensorsink.pond.rest.rioprime.types.PointsQuery;
import org.sensorsink.pond.rest.rioprime.types.PointsRequestList;
import org.sensorsink.pond.rest.rioprime.types.ProgramDetails;
import org.sensorsink.pond.rest.rioprime.types.ProgramRef;
import org.sensorsink.pond.rest.rioprime.types.ProgramsList;

public class RioPrimeClientHandlerMixin
    implements RioPrimeClientHandler, Initializable
{
    @This
    private State state;

    @Structure
    private ObjectFactory objectFactory;

    private Reference origin;

    private List<String> trackedPoints = new CopyOnWriteArrayList<>();
    private CountDownLatch countDownLatch;
    private PointsQuery pointsQuery;

    @Override
    public void initialize()
        throws InitializationException
    {
        Device device = state.device().get();
        Integer port = device.port().get();
        String host;
        if( port == null )
        {
            host = device.identity().get();
        }
        else
        {
            host = device.identity().get() + ":" + port;
        }
        origin = new Reference( "http://" + host + "/control/" );
        EntryPointHandler onResponseHandler = new EntryPointHandler();
        RestLink entryLink = RestLink.createLink( origin, "", Method.GET );
        entryLink.followLink( origin, getUser(), getPassword(), onResponseHandler );
    }

    private String getUser() {
        AccessCredentials credentials = state.device().get().credentials().get();
        return credentials.username().get();
    }
    private String getPassword() {
        AccessCredentials credentials = state.device().get().credentials().get();
        return credentials.password().get();
    }

    public void poll()
    {
        PointsRequestList pointsRequestList = new PointsRequestList();
        trackedPoints.forEach( pointsRequestList::addPoint );

        RestLink retrieveLink = findRetrieveLink();
        PointsResultHandler callback = new PointsResultHandler( state.device().get() );
        objectFactory.injectTo( callback );
        retrieveLink.followLinkWithContent( pointsRequestList, origin, getUser(), getPassword(), callback );
    }

    private RestLink findRetrieveLink()
    {
        return pointsQuery.getCommands()
            .stream()
            .filter( cmd -> cmd.getName().equals( "retrieve" ) )
            .findAny()
            .get().getLink();
    }

    private class EntryPointHandler
        implements Uniform
    {
        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                EntryPointBookmark bookmark = mapper.readValue( response.getEntityAsText(), EntryPointBookmark.class );

                String user = getUser();
                String password = getPassword();

                RestLink pointsLink = bookmark.getPoints();
                pointsLink.followLink( origin, user, password, new PointsHandler() );

                RestLink programsLink = bookmark.getPrograms();
                programsLink.followLink( origin, user, password, new ProgramsHandler() );
            }
            catch( IOException e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }
    }

    private class ProgramsHandler
        implements Uniform
    {
        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                ProgramsList programsList = mapper.readValue( response.getEntityAsText(), ProgramsList.class );
                ArrayList<ProgramRef> programRefs = programsList.getPrograms();

                countDownLatch = new CountDownLatch( programRefs.size() + 1 );  // Adding one to account for the PointsHandler
                String user = getUser();
                String password = getPassword();

                for( ProgramRef prog : programRefs )
                {
                    RestLink programLink = prog.getLoad();
                    programLink.followLink( origin, user, password, new ProgramHandler( prog.getName() ) );
                }
                boolean success = countDownLatch.await( 2, TimeUnit.MINUTES );
                if( !success )
                {
                    state.refreshOnNextPoll().set( true );
                }
            }
            catch( Exception e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }
    }

    private class ProgramHandler
        implements Uniform
    {
        private final String programName;

        public ProgramHandler( String programName )
        {
            this.programName = programName;
        }

        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                ProgramDetails program = mapper.readValue( response.getEntityAsText(), ProgramDetails.class );
                for( BlockDTO block : program.getBlocks() )
                {
                    trackedPoints.addAll(
                        block.getEdges().stream()
                            .filter( EdgeDTO::isTracked )
                            .map( edge -> programName + "." + block.getName() + "." + edge.getName() )
                            .collect( Collectors.toList() ) );
                }
                countDownLatch.countDown();
            }
            catch( IOException e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }
    }

    private class PointsHandler
        implements Uniform
    {
        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                pointsQuery = mapper.readValue( response.getEntityAsText(), PointsQuery.class );
                countDownLatch.countDown();
            }
            catch( IOException e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }
    }

    private class PointsResultHandler
        implements Uniform
    {
        @Structure
        private ValueBuilderFactory vbf;

        @Structure
        private UnitOfWorkFactory uowf;

        private final Device device;

        public PointsResultHandler( Device device )
        {
            this.device = device;
        }

        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                PointsData points = mapper.readValue( response.getEntityAsText(), PointsData.class );
                for( Map.Entry<String, PointData> entry : points.getData().entrySet() )
                {
                    PointInfo info = getPointInfo( entry.getKey() );
                    ValueBuilder<Point> builder = vbf.newValueBuilder( Point.class );

                    @SuppressWarnings( "unchecked" )
                    Point<String> prototype = builder.prototype();

                    prototype.info().set( info );
                    prototype.time().set( Instant.now() );

                    //noinspection unchecked
                    prototype.value().set( entry.getValue().getValue() );
                    state.callback().get().accept( builder.newInstance() );
                }
            }
            catch( IOException e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }

        private PointInfo getPointInfo( String pointName )
        {
            String identity = device.identity().get() + "/" + pointName;
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                return uow.get( PointInfo.class, identity );
            }
            catch( NoSuchEntityException e )
            {
                EntityBuilder<PointInfo> builder = uow.newEntityBuilder( PointInfo.class, identity );
                PointInfo instance = builder.instance();
                instance.name().set( pointName );
                instance.device().set( device );
                return builder.newInstance();
            }
        }
    }
}
