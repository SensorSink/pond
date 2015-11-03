package org.sensorsink.rest.rioprime;

import java.io.Serializable;
import org.sensorsink.trucking.rest.common.RestLink;

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
