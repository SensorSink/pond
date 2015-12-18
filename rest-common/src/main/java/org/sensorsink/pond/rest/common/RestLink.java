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

package org.sensorsink.pond.rest.common;

import java.io.Serializable;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Uniform;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.StringRepresentation;
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

    public void followLink( Reference hostBase, String user, String password, Uniform callback )
    {
        ClientResource resource = createClient( hostBase, user, password, callback );
        Request request = resource.getRequest();
        String msg = "Request: " + request.getResourceRef() + "\n" + request.getEntityAsText();
        System.out.println( msg );
        resource.handle();
    }

    public void followLinkWithContent( String entityAsText,
                                       Reference base,
                                       String user,
                                       String password,
                                       Uniform callback
    )
    {
        System.out.println(entityAsText);
        final ClientResource resource = createClient( base, user, password, callback );
        resource.getRequest().setEntity( new StringRepresentation( entityAsText, MediaType.APPLICATION_JSON ) );
        Request request = resource.getRequest();
        String msg = "Request: " + request.getResourceRef() + "\n" + request.getEntityAsText();
        System.out.println( msg );
        resource.handle();
    }

    public <T> void followLinkWithContent( T value, Reference base, String user, String password, Uniform callback )
    {
        final ClientResource resource = createClient( base, user, password, callback );
        resource.getRequest().setEntity( new JacksonRepresentation<>( value ) );
        Request request = resource.getRequest();
        String msg = "Request: " + request.getResourceRef() + "\n" + request.getEntityAsText();
        System.out.println( msg );
        resource.handle();
    }

    private ClientResource createClient( Reference base, String user, String password, Uniform callback )
    {
        Reference uri = new Reference( base, getPath() );
        final ClientResource resource = new ClientResource( new Context(), uri );
        if( user != null )
        {
            resource.setChallengeResponse( ChallengeScheme.HTTP_BASIC, user, password );
        }
        resource.setRetryOnError( false );
        resource.setOnResponse( ( request, response ) -> {
            try
            {
                String msg = "Response: " + resource.getRequest().getResourceRef() + "\n" + response.getEntityAsText();
                System.out.println( msg );
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
        resource.setMethod( Method.valueOf( getMethod() ) );
        return resource;
    }

    @Override
    public String toString()
    {
        return "Link{" + "method='" + method + '\'' + ", href='" + linkRef + '\'' + '}';
    }
}
