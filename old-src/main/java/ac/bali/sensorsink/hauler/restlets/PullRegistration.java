package ac.bali.sensorsink.hauler.restlets;

public class PullRegistration
{
    private String type;
    private String host;
    private String port;
    private String user;
    private String pass;

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost( String host )
    {
        this.host = host;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort( String port )
    {
        this.port = port;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser( String user )
    {
        this.user = user;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass( String pass )
    {
        this.pass = pass;
    }
}
