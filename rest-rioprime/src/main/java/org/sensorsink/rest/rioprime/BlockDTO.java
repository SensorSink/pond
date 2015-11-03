package org.sensorsink.rest.rioprime;

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
