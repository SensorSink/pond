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

package org.sensorsink.pond.rest.rioprime.types;

import java.io.Serializable;

public class ConnectionDTO
    implements Serializable
{
    private String from;
    private String to;
    private String type;

    public ConnectionDTO()
    {
    }

    public ConnectionDTO( String type, String from, String to )
    {
        int pos = type.lastIndexOf( '.' );
        if( pos > 0 )
        {
            type = type.substring( pos + 1 );
        }
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public String getType()
    {
        return type;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public void setFrom( String from )
    {
        this.from = from;
    }

    public void setTo( String to )
    {
        this.to = to;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "{ '" ).append( from ).append( '\'' );
        sb.append( "---(" ).append( type );
        sb.append( ")--> '" ).append( to ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }
}
