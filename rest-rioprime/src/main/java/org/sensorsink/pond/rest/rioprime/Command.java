/*
 * Copyright 2015 Niclas Hedhman, niclas@hedhman.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sensorsink.pond.rest.rioprime;

import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.sensorsink.pond.rest.common.RestLink;

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
