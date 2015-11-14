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

public class EntryPointBookmark
    implements Serializable
{
    private String version;
    private RestLink programs;
    private RestLink blocktypes;
    private RestLink views;
    private RestLink timeseries;
    private RestLink reports;
    private RestLink io;
    private RestLink points;
    private RestLink settings;
    private RestLink users;
    private RestLink alarms;
    private RestLink system;
    private RestLink fileUploadPath;

    public String getVersion()
    {
        return version;
    }

    public RestLink getPrograms()
    {
        return programs;
    }

    public RestLink getBlocktypes()
    {
        return blocktypes;
    }

    public RestLink getViews()
    {
        return views;
    }

    public RestLink getTimeseries()
    {
        return timeseries;
    }

    public RestLink getReports()
    {
        return reports;
    }

    public RestLink getIo()
    {
        return io;
    }

    public RestLink getPoints()
    {
        return points;
    }

    public RestLink getSettings()
    {
        return settings;
    }
    public RestLink getSystem()
    {
        return system;
    }

    public RestLink getUsers()
    {
        return users;
    }

    public RestLink getFileUploadPath()
    {
        return fileUploadPath;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public void setPrograms( RestLink programs )
    {
        this.programs = programs;
    }

    public void setBlocktypes( RestLink blocktypes )
    {
        this.blocktypes = blocktypes;
    }

    public void setViews( RestLink views )
    {
        this.views = views;
    }

    public void setTimeseries( RestLink timeseries )
    {
        this.timeseries = timeseries;
    }

    public void setReports( RestLink reports )
    {
        this.reports = reports;
    }

    public void setIo( RestLink io )
    {
        this.io = io;
    }

    public void setPoints( RestLink points )
    {
        this.points = points;
    }

    public void setSettings( RestLink settings )
    {
        this.settings = settings;
    }

    public void setUsers( RestLink users )
    {
        this.users = users;
    }

    public void setAlarms( RestLink alarms )
    {
        this.alarms = alarms;
    }

    public void setSystem( RestLink system )
    {
        this.system = system;
    }

    public void setFileUploadPath( RestLink fileUploadPath )
    {
        this.fileUploadPath = fileUploadPath;
    }
}
