package org.sensorsink.rest.rioprime;

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
