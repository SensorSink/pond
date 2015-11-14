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

public class EdgeDTO
    implements Serializable
{
    private String name;
    private EdgeType io;
    private String type;
    private String value;
    private Boolean track;
    private Double low;
    private Double high;

    public EdgeDTO()
    {
    }

    public EdgeDTO( String name, EdgeType io, String type, String value, Boolean track, Double low, Double high )
    {
        this.name = name;
        this.io = io;
        this.type = type;
        this.value = value;
        this.track = track;
        this.low = low;
        this.high = high;
    }

    public String getName()
    {
        return name;
    }

    public EdgeType getIo()
    {
        return io;
    }

    public String getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setIo( EdgeType io )
    {
        this.io = io;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "{ " ).append( io );
        sb.append( ": " ).append( name );
        sb.append( ":" ).append( type );
        sb.append( " = '" ).append( value ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean isTracked()
    {
        if( track == null )
        {
            return false;
        }
        return track;
    }

    public void setTracked( boolean track )
    {
        this.track = track;
    }

    public Double getLow()
    {
        return low;
    }

    public void setLow( Double low )
    {
        this.low = low;
    }

    public Double getHigh()
    {
        return high;
    }

    public void setHigh( Double high )
    {
        this.high = high;
    }
}
