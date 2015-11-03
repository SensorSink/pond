package org.sensorsink.rest.rioprime;

import java.io.Serializable;
import org.sensorsink.trucking.rest.common.RestLink;

public class ProgramRef
    implements Serializable
{
    private String name;
    private RestLink load;
    private RestLink delete;
    private RestLink save;

    public ProgramRef()
    {
    }

    public ProgramRef( String name, RestLink loadLink, RestLink deleteLink, RestLink saveLink )
    {
        this.name = name;
        this.load = loadLink;
        this.delete = deleteLink;
        this.save = saveLink;
    }

    public String getName()
    {
        return name;
    }

    public RestLink getLoad()
    {
        return load;
    }

    public RestLink getDelete()
    {
        return delete;
    }

    public RestLink getSave()
    {
        return save;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setLoad( RestLink load )
    {
        this.load = load;
    }

    public void setDelete( RestLink delete )
    {
        this.delete = delete;
    }

    public void setSave( RestLink save )
    {
        this.save = save;
    }
}
