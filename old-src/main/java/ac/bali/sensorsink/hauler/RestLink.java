package ac.bali.sensorsink.hauler;

import java.io.Serializable;
import org.restlet.Context;
import org.restlet.Uniform;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.resource.ClientResource;

public class RestLink
    implements Serializable
{
    private String method;
    private String linkRef;

    public RestLink()
    {
    }

    public RestLink( String method, String linkRef )
    {
        this.method = method;
        this.linkRef = linkRef;
    }

    public String getMethod()
    {
        return method;
    }

    public String getPath()
    {
        return linkRef;
    }

    public void setMethod( String method )
    {
        this.method = method;
    }

    public void setPath( String linkRef )
    {
        this.linkRef = linkRef;
    }

    public static RestLink createLink( Reference base, String name, Method action )
    {
        return new RestLink( action.getName(), base.toUri().resolve( name ).getPath() );
    }

    public void followLink( Reference base, String user, String password, Uniform callback )
    {
        Reference uri = new Reference( base, getPath() );
        final ClientResource resource = new ClientResource( new Context(), uri );
        if( user != null )
        {
            resource.setChallengeResponse( ChallengeScheme.HTTP_BASIC, user, password );
        }
        resource.setOnResponse( ( request, response ) -> {
            try
            {
                callback.handle( request, response );
            }
            catch( RuntimeException e )
            {
                e.printStackTrace();
            }
            finally
            {
                resource.release();
            }
        } );
        resource.setMethod( Method.GET );
        resource.handle();
    }

    public <T> void followLinkWithContent( T value, Reference base, String user, String password, Uniform callback )
    {
        Reference uri = new Reference( base, getPath() );
        final ClientResource resource = new ClientResource( new Context(), uri );
        if( user != null )
        {
            resource.setChallengeResponse( ChallengeScheme.HTTP_BASIC, user, password );
        }
        resource.setOnResponse( ( request, response ) -> {
            try
            {
                callback.handle( request, response );
            }
            catch( RuntimeException e )
            {
                e.printStackTrace();
            }
            finally
            {
                resource.release();
            }
        } );
        resource.getRequest().setEntity( new JacksonRepresentation<T>( value ) );
        resource.setMethod( Method.GET );
        resource.handle();
    }


    @Override
    public String toString()
    {
        return "Link{" + "method='" + method + '\'' + ", href='" + linkRef + '\'' + '}';
    }
}
