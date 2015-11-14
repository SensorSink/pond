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
        return new RestLink( action.getName(), base.toUri().resolve( name ).getPath() + "/" );
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
        final StringBuilder sb = new StringBuilder( "Link{" );
        sb.append( "method='" ).append( method ).append( '\'' );
        sb.append( ", href='" ).append( linkRef ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }
}
