package ac.bali.sensorsink.hauler.restlets;

public class HostDetails
{
    private String type;
    private String hostname;
    private String port;
    private String username;
    private String password;
    private String creator;
    private String created;

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname( String hostname )
    {
        this.hostname = hostname;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort( String port )
    {
        this.port = port;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated( String created )
    {
        this.created = created;
    }

}
