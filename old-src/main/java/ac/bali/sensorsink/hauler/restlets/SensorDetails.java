package ac.bali.sensorsink.hauler.restlets;

import ac.bali.sensorsink.hauler.RestLink;
import org.restlet.data.Method;
import org.restlet.data.Reference;

public class SensorDetails
{
    private String id;
    private RestLink self;
    private String name;
    private long created;
    private String creator;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public RestLink getSelf()
    {
        return self;
    }

    public void setSelf( RestLink self )
    {
        this.self = self;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public long getCreated()
    {
        return created;
    }

    public void setCreated( long created )
    {
        this.created = created;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

}
