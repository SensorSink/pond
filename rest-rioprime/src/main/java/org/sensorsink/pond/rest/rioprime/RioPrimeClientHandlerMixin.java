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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.mixin.InitializationException;
import org.apache.zest.api.object.ObjectFactory;
import org.apache.zest.api.property.Property;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;
import org.apache.zest.library.restlet.identity.IdentityManager;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.samples.Sample;
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
    implements RioPrimeClientHandler
{
    @This
    private State state;

    @Structure
    private ObjectFactory objectFactory;

    private Reference origin;

    private List<String> trackedPoints = new CopyOnWriteArrayList<>();
    private CountDownLatch countDownLatch;
    private PointsQuery pointsQuery;
    private volatile boolean connected = false;

    public void connect()
        throws InitializationException
    {
        if( connected )
        {
            return;
        }
        Device device = state.device().get();
        Integer port = device.port().get();
        String hostName;
        if( port != null && port > 0 )
        {
            hostName = device.hostName() + ":" + port;
        }
        else
        {
            hostName = device.hostName() + ":8778";
        }

        origin = new Reference( "http://" + hostName + contextPath( hostName ) );
        EntryPointHandler onResponseHandler = new EntryPointHandler();
        RestLink entryLink = RestLink.createLink( origin, "", Method.GET );
        entryLink.followLink( origin, getUser(), getPassword(), onResponseHandler );
        connected = true;
    }

    private String contextPath( String hostName )
    {
        if( hostName.startsWith( "localhost" ) )
        {
            return "/c/";
        }
        if( hostName.startsWith( "127.0.0.1" ) )
        {
            return "/c/";
        }
        return "/control/";
    }

    private String getUser()
    {
        AccessCredentials credentials = state.device().get().credentials().get();
        if( credentials == null )
        {
            return null;
        }
        return credentials.username().get();
    }

    private String getPassword()
    {
        AccessCredentials credentials = state.device().get().credentials().get();
        if( credentials == null )
        {
            return null;
        }
        return credentials.password().get();
    }

    public void poll()
    {
        connect();
        PointsRequestList pointsRequestList = new PointsRequestList();
        trackedPoints.forEach( pointsRequestList::addPoint );

        RestLink retrieveLink = findRetrieveLink();
        if( retrieveLink == null )
        {
            return;
        }
        PointsResultHandler callback =
            new PointsResultHandler( state.device().get(), state.callback().get(), state.refreshOnNextPoll() );

        objectFactory.injectTo( callback );
        retrieveLink.followLinkWithContent( pointsRequestList, origin, getUser(), getPassword(), callback );
    }

    private RestLink findRetrieveLink()
    {
        if( pointsQuery == null )
        {
            return null;
        }
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

                countDownLatch = new CountDownLatch( programRefs.size() );
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
            }
            catch( IOException e )
            {
                state.refreshOnNextPoll().set( true );
            }
        }
    }

    public static class PointsResultHandler
        implements Uniform
    {
        @Structure
        private ValueBuilderFactory vbf;

        @Structure
        private UnitOfWorkFactory uowf;

        @Service
        IdentityManager identityManager;

        private final Device device;
        private final Consumer<Sample> callback;
        private final Property<Boolean> refreshOnNextPoll;

        public PointsResultHandler( Device device, Consumer<Sample> callback, Property<Boolean> refreshOnNextPoll )
        {
            this.device = device;
            this.callback = callback;
            this.refreshOnNextPoll = refreshOnNextPoll;
        }

        @Override
        public void handle( Request request, Response response )
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                PointsData points = mapper.readValue( response.getEntityAsText(), PointsData.class );
                Map<String, Double> data = new HashMap<>();
                for( Map.Entry<String, PointData> entry : points.getData().entrySet() )
                {
                    data.put( entry.getKey(), entry.getValue().getValue() );
                }
                ValueBuilder<Sample> builder = vbf.newValueBuilder( Sample.class );
                builder.prototype().device().set( device );
                builder.prototype().time().set( ZonedDateTime.now().format( DateTimeFormatter.ISO_INSTANT ));
                builder.prototype().values().set(data);
                callback.accept( builder.newInstance() );
            }
            catch( IOException e )
            {
                refreshOnNextPoll.set( true );
            }
        }
    }
}
