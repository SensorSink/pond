package org.sensorsink.rest.rioprime;

import java.util.HashMap;

/**
 * {"data":
 * {
 * "INNE.indoor.output" : {"value":"31.9","get":{"method":"GET","path":"/control/points/INNE.indoor.output/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.output/"}},
 * "INNE.indoor.indoor" : {"value":"16.7","get":{"method":"GET","path":"/control/points/INNE.indoor.indoor/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.indoor/"}}
 * }
 * }
 */
public class RioPoints
{
    private HashMap<String,RioPoint> data;

    public RioPoints()
    {
        data = new HashMap<>();
    }

    public RioPoints( HashMap<String, RioPoint> data )
    {
        this.data = data;
    }

    public HashMap<String, RioPoint> getData()
    {
        return data;
    }

    public void setData( HashMap<String, RioPoint> data )
    {
        this.data = data;
    }
}
