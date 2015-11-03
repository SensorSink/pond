package ac.bali.sensorsink.hauler;

import java.util.ArrayList;

public class Host
{
    private String host;
    private int port;
    private ArrayList<String> points;

    public String getHost()
    {
        return host;
    }

    public void setHost( String host )
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
    }

    public ArrayList<String> getPoints()
    {
        return points;
    }

    public void setPoints( ArrayList<String> points )
    {
        this.points = points;
    }
}
