package org.sensorsink.rest.rioprime;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.composite.TransientBuilder;
import org.apache.zest.api.composite.TransientBuilderFactory;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.mixin.Initializable;
import org.apache.zest.api.mixin.InitializationException;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.object.ObjectFactory;
import org.apache.zest.api.property.Property;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;
import org.sensorsink.trucking.rest.common.RestLink;

@Mixins( PrimeReader.Mixin.class )
public interface PrimeReader
{

    String pointsUrl();

    interface State
    {
        Property<String> host();

        Property<String> user();

        Property<String> password();

        @Optional
        Property<String> pointsUrl();
    }

    class Builder
    {
        @Structure
        private TransientBuilderFactory tbf;

        public PrimeReader create( String host, String user, String password )
        {
            TransientBuilder<PrimeReader> builder = tbf.newTransientBuilder( PrimeReader.class );
            PrimeReader.State prototype = builder.prototypeFor( State.class );
            prototype.host().set( host );
            prototype.user().set( user );
            prototype.password().set( password );
            return builder.newInstance();
        }
    }

    class Mixin
        implements PrimeReader, Initializable
    {
        private final RestLink entrypoint = new RestLink( "GET", "/" );

        @This
        private State state;

        @Structure
        private ObjectFactory objectFactory;

        private Reference origin;

        @Override
        public String pointsUrl()
        {
            return state.pointsUrl().get();
        }

        @Override
        public void initialize()
            throws InitializationException
        {
            origin = new Reference( "http://" + state.host() );
            EntryPointHandler onResponseHandler = new EntryPointHandler();
            ClientResource clientResource = createClientResource( entrypoint, onResponseHandler );
            clientResource.handle();
        }

        private ClientResource createClientResource( RestLink link, Uniform onResponseHandler )
        {
            Reference reference = new Reference( origin, link.getPath() );
            ClientResource clientResource = new ClientResource( reference.getTargetRef() );
            clientResource.setMethod( Method.valueOf( link.getMethod() ) );
            clientResource.setOnResponse( onResponseHandler );
            setupClient( clientResource );
            return clientResource;
        }

        private void setupClient( ClientResource clientResource )
        {
            clientResource.setRetryOnError( true );
            clientResource.setRetryAttempts( 2 );
            clientResource.setFollowingRedirects( true );
            clientResource.accept( MediaType.APPLICATION_JSON );
            clientResource.accept( MediaType.APPLICATION_XML );
            ChallengeResponse authentication = challenge();
            clientResource.setChallengeResponse( authentication );
            clientResource.setLoggable( true );
        }

        private ChallengeResponse challenge()
        {
            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse response = new ChallengeResponse( scheme, state.user().get(), state.password().get() );
            response.setRealm( "Balistic Rio" );
            return response;
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
                    RestLink pointsLink = bookmark.getPoints();
                    ClientResource client = createClientResource( pointsLink, new PointsHandler() );
                    client.handle();
                }
                catch( IOException e )
                {
                    // TODO
                }
            }
        }

        private static class PointsHandler
            implements Uniform
        {
            @Override
            public void handle( Request request, Response response )
            {
                try
                {
                    ObjectMapper mapper = new ObjectMapper();
                    PointsRequestList points = mapper.readValue( response.getEntityAsText(), PointsRequestList.class );
                    System.out.println(points);
                }
                catch( IOException e )
                {
                    // TODO
                }
            }
        }

    }
}
