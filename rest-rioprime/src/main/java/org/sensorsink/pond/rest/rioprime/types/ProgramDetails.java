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
import java.util.ArrayList;

public class ProgramDetails
    implements Serializable
{

    private String name;

    /**
     * Block instances.
     * key = instance name
     * value = block type name, as a fully qualified class name.
     */
    private ArrayList<BlockDTO> blocks;

    /**
     * Connections between Edges.
     * key = Input name
     * value = Output name
     */
    private ArrayList<ConnectionDTO> connections;

    public ProgramDetails()
    {
        blocks = new ArrayList<BlockDTO>();
        connections = new ArrayList<ConnectionDTO>();
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<BlockDTO> getBlocks()
    {
        return blocks;
    }

    public ArrayList<ConnectionDTO> getConnections()
    {
        return connections;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setBlocks( ArrayList<BlockDTO> blocks )
    {
        this.blocks = blocks;
    }

    public void setConnections( ArrayList<ConnectionDTO> connections )
    {
        this.connections = connections;
    }
}
