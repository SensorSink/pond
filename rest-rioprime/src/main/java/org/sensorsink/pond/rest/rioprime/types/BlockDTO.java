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
import java.util.ArrayList;

public class BlockDTO
    implements Serializable
{
    private String name;
    private String type;
    private ArrayList<EdgeDTO> edges;

    public BlockDTO()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public ArrayList<EdgeDTO> getEdges()
    {
        return edges;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setEdges( ArrayList<EdgeDTO> edges )
    {
        this.edges = edges;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "{ \"name\": \"" ).append( name ).append( "\", " );
        sb.append( " \"type\": \"" ).append( type ).append( "\", " );
        sb.append( " \"edges\": [" );
        boolean first = true;
        for( EdgeDTO edge : edges )
        {
            if( !first )
            {
                sb.append( ", " );
            }
            first = false;
            sb.append( edge );
        }
        sb.append( "]" );
        sb.append( '}' );
        return sb.toString();
    }
}
