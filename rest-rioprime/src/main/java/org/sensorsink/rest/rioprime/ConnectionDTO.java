package org.sensorsink.rest.rioprime;

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
