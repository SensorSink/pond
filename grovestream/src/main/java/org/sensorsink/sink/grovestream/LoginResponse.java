package org.sensorsink.sink.grovestream;

public class LoginResponse
{
    private String message;
    private String userFirstName;
    private String userLastName;
    private String userUid;
    private String sessionUid;
    private boolean success;
    private OrganizationRef[] organization;

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public String getUserFirstName()
    {
        return userFirstName;
    }

    public void setUserFirstName( String userFirstName )
    {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName()
    {
        return userLastName;
    }

    public void setUserLastName( String userLastName )
    {
        this.userLastName = userLastName;
    }

    public String getUserUid()
    {
        return userUid;
    }

    public void setUserUid( String userUid )
    {
        this.userUid = userUid;
    }

    public String getSessionUid()
    {
        return sessionUid;
    }

    public void setSessionUid( String sessionUid )
    {
        this.sessionUid = sessionUid;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess( boolean success )
    {
        this.success = success;
    }

    public OrganizationRef[] getOrganization()
    {
        return organization;
    }

    public void setOrganization( OrganizationRef[] organization )
    {
        this.organization = organization;
    }
}
