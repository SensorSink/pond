package ac.bali.sensorsink.hauler.restlets;

import ac.bali.sensorsink.hauler.RestLink;

public class HostRef
{
    private String name;
    private RestLink getMethod;
    private RestLink putMethod;
    private RestLink deleteMethod;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public RestLink getGetMethod()
    {
        return getMethod;
    }

    public void setGetMethod( RestLink getMethod )
    {
        this.getMethod = getMethod;
    }

    public RestLink getPutMethod()
    {
        return putMethod;
    }

    public void setPutMethod( RestLink putMethod )
    {
        this.putMethod = putMethod;
    }

    public RestLink getDeleteMethod()
    {
        return deleteMethod;
    }

    public void setDeleteMethod( RestLink deleteMethod )
    {
        this.deleteMethod = deleteMethod;
    }
}
