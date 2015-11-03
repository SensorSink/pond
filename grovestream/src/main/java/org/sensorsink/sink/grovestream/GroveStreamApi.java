package org.sensorsink.sink.grovestream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.sensorsink.trucking.rest.common.RestLink;

public class GroveStreamApi
{

    public static final String GROVESTREAMS_API = "http://grovestreams.com/api/";

    public final Reference base;
    private String sessionId;

    public GroveStreamApi()
    {
        base = new Reference( GROVESTREAMS_API );
    }

    public void login( String email, String password, Consumer<LoginResponse> consumer )
    {
        LoginRequest login = new LoginRequest( email, password );
        RestLink link = RestLink.createLink( base, "login", Method.POST );
        link.followLinkWithContent( login, base, null, null, ( req, resp ) -> {
            LoginResponse loginResponse = deserialize( LoginResponse.class, resp );
            if( loginResponse == null )
            {
                return;
            }
            this.sessionId = loginResponse.getSessionUid();
            consumer.accept( loginResponse );
        } );
    }

    public void checkName( String name, Consumer<Boolean> consumer )
    {
        RestLink link = RestLink.createLink( base, "organization/name_exists/" + name, Method.GET );

    }

    public void registerOrganization( Organization org )
    {
        RestLink link = RestLink.createLink( base, "organization/new", Method.PUT );
        String json = serialize( org );
        link.followLinkWithContent( base, null, null, json, ( request, response ) -> {
            if( response.getStatus().equals( Status.SUCCESS_OK ) )
            {
            }
        } );
    }

    private String serialize( Organization org )
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString( org );
        }
        catch( JsonProcessingException e )
        {
            e.printStackTrace();
            return null;
        }
    }

    private <T> T deserialize( Class<T> type, Response response )
    {
        try
        {
            return new ObjectMapper().readValue( response.getEntityAsText(), type );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    private void saveEntity( String uid, String entityAsText )
    {
        try (FileWriter fw = new FileWriter( "entities/" + uid + ".json" ))
        {
            BufferedWriter writer = new BufferedWriter( fw );
            writer.write( entityAsText );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
