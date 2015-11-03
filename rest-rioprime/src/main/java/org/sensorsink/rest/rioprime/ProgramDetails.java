package org.sensorsink.rest.rioprime;

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
