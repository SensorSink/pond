package org.sensorsink.rest.rioprime;

import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.sensorsink.trucking.rest.common.RestLink;

public class Command
{
    private String name;
    private RestLink link;

    public Command()
    {
    }

    public Command( String name, RestLink link )
    {
        this.name = name;
        this.link = link;
    }

    public String getName()
    {
        return name;
    }

    public RestLink getLink()
    {
        return link;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setLink( RestLink link )
    {
        this.link = link;
    }

    public static RestLink createLink( Reference base, String name, Method action )
    {
        return new RestLink( action.getName(), base.toUri().resolve( name ).getPath() + "/" );
    }

    public static Command createCommand( Reference base, String name, Method action )
    {
        RestLink createLink = new RestLink();
        createLink.setMethod( action.getName() );
        createLink.setPath( new Reference( base, name ).getTargetRef().getPath() );
        return new Command( name, createLink );
    }
}
