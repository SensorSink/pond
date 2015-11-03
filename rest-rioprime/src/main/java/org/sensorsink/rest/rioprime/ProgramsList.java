package org.sensorsink.rest.rioprime;

import java.io.Serializable;
import java.util.ArrayList;

public class ProgramsList
    implements Serializable
{
    private ArrayList<Command> commands;
    private ArrayList<ProgramRef> programs;

    public ProgramsList()
    {
    }

    public ArrayList<Command> getCommands()
    {
        return commands;
    }

    public void setCommands( ArrayList<Command> commands )
    {
        this.commands = commands;
    }

    public ArrayList<ProgramRef> getPrograms()
    {
        return programs;
    }

    public void setPrograms( ArrayList<ProgramRef> programs )
    {
        this.programs = programs;
    }
}
