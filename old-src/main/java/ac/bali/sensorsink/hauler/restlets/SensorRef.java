package ac.bali.sensorsink.hauler.restlets;

import ac.bali.sensorsink.RestLink;

public class SensorRef
{
    private String id;
    private RestLink getMethod;
    private RestLink postMethod;
    private RestLink deleteMethod;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public RestLink getPostMethod()
    {
        return postMethod;
    }

    public void setPostMethod( RestLink postMethod )
    {
        this.postMethod = postMethod;
    }

    public RestLink getDeleteMethod()
    {
        return deleteMethod;
    }

    public void setDeleteMethod( RestLink deleteMethod )
    {
        this.deleteMethod = deleteMethod;
    }

    public RestLink getGetMethod()
    {
        return getMethod;
    }

    public void setGetMethod( RestLink getMethod )
    {
        this.getMethod = getMethod;
    }
}
