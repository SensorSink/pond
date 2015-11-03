package org.sensorsink.trucking.security;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ClientInfo;
import org.restlet.security.Enroler;
import org.restlet.security.Verifier;

public class HardcodedSecurity
    implements Verifier,Enroler
{
    @Override
    public void enrole( ClientInfo clientInfo )
    {

    }

    @Override
    public int verify( Request request, Response response )
    {
        return Verifier.RESULT_VALID;
    }
}
