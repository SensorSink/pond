package org.sensorsink.trucking.rest;

import org.apache.zest.bootstrap.AssemblyException;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class SensorSink
{
    private static SensorSink sensorSink;
    private Component server;
    private RestApplication application;

    public SensorSink()
        throws AssemblyException
    {
        server = new Component();
        application = new RestApplication( server.getContext() );
        server.getServers().add( Protocol.HTTP, 8778 );
        server.getClients().add( Protocol.CLAP );
        server.getClients().add( Protocol.FILE );
        server.getClients().add( Protocol.HTTP );
        server.getDefaultHost().attach( "/g", application );
    }

    public void start()
        throws Exception
    {
        server.start();
    }

    public void stop()
        throws Exception
    {
        if( server != null )
        {
            server.stop();
        }
    }

    public static void main( String[] args )
        throws Exception
    {
        sensorSink = new SensorSink();
        addShutdownHook( sensorSink );
        sensorSink.start();
    }

    private static void addShutdownHook( final SensorSink sensorSink )
    {
        Runtime.getRuntime().addShutdownHook( new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    sensorSink.stop();
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } ) );
    }
}
