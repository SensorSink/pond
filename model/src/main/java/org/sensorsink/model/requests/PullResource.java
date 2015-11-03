package org.sensorsink.model.requests;

import java.util.function.Consumer;
import org.sensorsink.rest.rioprime.RioPrimePointRetriever;
import org.sensorsink.rest.rioprime.RioPoint;

public class PullResource
{
    protected void pull( String type, String host, String port ,String user, String password    )
    {
        if( type.equals( "rio910" ) )
        {
            Consumer<RioPoint> pointConsumer = new PointConsumer( host );

            new RioPrimePointRetriever( host, user,  password, pointConsumer );
        }
    }

    private static class PointConsumer
        implements Consumer<RioPoint>
    {
        private final String host;

        public PointConsumer( String host )
        {
            this.host = host;
        }

        @Override
        public void accept( RioPoint data )
        {

        }
    }
}
