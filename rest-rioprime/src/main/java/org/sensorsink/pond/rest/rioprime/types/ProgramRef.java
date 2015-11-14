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

package org.sensorsink.pond.rest.rioprime.types;

import java.io.Serializable;
import org.sensorsink.pond.rest.common.RestLink;

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
