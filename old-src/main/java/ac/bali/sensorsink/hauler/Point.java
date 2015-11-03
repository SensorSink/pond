package ac.bali.sensorsink.hauler;

import org.sensorsink.model.rio.EdgeDTO;

public class Point
{
    private String fullname;
    private EdgeDTO edge;

    public Point( String fullname, EdgeDTO edge )
    {
        this.fullname = fullname;
        this.edge = edge;
    }

    public String fullname()
    {
        return fullname;
    }

    public EdgeDTO edge()
    {
        return edge;
    }
}
