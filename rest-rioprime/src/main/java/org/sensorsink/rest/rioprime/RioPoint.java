package org.sensorsink.rest.rioprime;

import org.sensorsink.trucking.rest.common.RestLink;

/**
 * {"data":
 * {
 * "INNE.indoor.output" : {"value":"31.9","get":{"method":"GET","path":"/control/points/INNE.indoor.output/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.output/"}},
 * "INNE.indoor.indoor" : {"value":"16.7","get":{"method":"GET","path":"/control/points/INNE.indoor.indoor/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.indoor/"}}
 * }
 * }
 */
public class RioPoint
{
    private String value;
    private RestLink get;
    private RestLink put;

    public RioPoint()
    {
    }

    public RioPoint( String value, RestLink get, RestLink put )
    {
        this.value = value;
        this.get = get;
        this.put = put;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public RestLink getGet()
    {
        return get;
    }

    public void setGet( RestLink get )
    {
        this.get = get;
    }

    public RestLink getPut()
    {
        return put;
    }

    public void setPut( RestLink put )
    {
        this.put = put;
    }
}
