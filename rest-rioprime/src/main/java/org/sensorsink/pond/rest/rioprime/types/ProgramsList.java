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
import org.sensorsink.pond.rest.rioprime.Command;

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
