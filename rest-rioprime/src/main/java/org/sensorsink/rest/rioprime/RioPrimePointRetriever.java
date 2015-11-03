package org.sensorsink.rest.rioprime;

import java.util.function.Consumer;
import org.apache.zest.api.injection.scope.Service;
import org.restlet.resource.ClientResource;

public class RioPrimePointRetriever
{
    private final String host;
    private final String user;
    private final String password;
    private final Consumer<RioPoint> pointConsumer;

    @Service
    private PrimeReader primeReader;

    public RioPrimePointRetriever( String host, String user, String password, Consumer<RioPoint> pointConsumer )
    {
        this.host = host;
        this.user = user;
        this.password = password;
        this.pointConsumer = pointConsumer;
    }

    public void fetch() {
        ClientResource entryPoint =  new ClientResource( primeReader.pointsUrl() );
    }
}
