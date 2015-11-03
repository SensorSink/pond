package org.sensorsink.rest.rioprime;

import java.io.Serializable;
import java.util.ArrayList;

public class PointsRequestList
    implements Serializable
{
    private ArrayList<String> points;

    public PointsRequestList()
    {
        points = new ArrayList<>();
    }

    public ArrayList<String> getPoints()
    {
        return points;
    }

    public void setPoints( ArrayList<String> points )
    {
        this.points = points;
    }

    public void addPoint( String point )
    {
        synchronized( this )
        {
            this.points.add( point );
        }
    }
}
