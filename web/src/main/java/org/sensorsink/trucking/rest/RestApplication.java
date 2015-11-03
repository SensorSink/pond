package org.sensorsink.trucking.rest;

import java.util.logging.Level;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.object.ObjectFactory;
import org.apache.zest.api.service.ServiceFinder;
import org.apache.zest.api.structure.Application;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.library.restlet.ZestEntityRestlet;
import org.apache.zest.library.restlet.crud.EntityListResource;
import org.apache.zest.library.restlet.crud.EntityResource;
import org.apache.zest.library.restlet.resource.CreationResource;
import org.apache.zest.library.restlet.resource.DefaultResourceFactoryImpl;
import org.apache.zest.library.restlet.resource.EntryPoint;
import org.apache.zest.library.restlet.resource.EntryPointResource;
import org.apache.zest.library.restlet.resource.ResourceFactory;
import org.apache.zest.library.restlet.resource.ServerResource;
import org.apache.zest.library.restlet.serialization.ZestConverter;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.Engine;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.sensorsink.model.devices.Device;
import org.sensorsink.trucking.security.SecurityService;
import org.sensorsink.trucking.bootstrap.SensorSinkAssembler;
import org.sensorsink.trucking.bootstrap.connectivity.ConnectivityLayer;
import org.sensorsink.trucking.bootstrap.connectivity.RestModule;

public class RestApplication extends org.restlet.Application
{
    private final org.apache.zest.api.structure.Application qi4j;
    private ServiceFinder serviceFinder;
    private ObjectFactory objectFactory;

    private Router router;

    public RestApplication( Context context )
        throws AssemblyException
    {
        super( context );
        try
        {
            SensorSinkAssembler assembler = new SensorSinkAssembler( Application.Mode.development );
            assembler.initialize();
            qi4j = assembler.application();
            setName( qi4j.name() );
        }
        catch( Throwable e )
        {
            e.printStackTrace();
            getLogger().log( Level.SEVERE, "Unable to start Qi4j application.", e );
            throw new InternalError( "Unable to start Qi4j application.", e );
        }
        getLogger().info( "RestApplication successfully created." );
    }

    @Override
    public synchronized void start()
        throws Exception
    {
        qi4j.activate();
        serviceFinder = qi4j.findModule( ConnectivityLayer.NAME, RestModule.NAME );
        objectFactory = qi4j.findModule( ConnectivityLayer.NAME, RestModule.NAME );
        super.start();
    }

    @Override
    public synchronized void stop()
        throws Exception
    {
        super.stop();
        qi4j.passivate();
    }

    @Override
    public Restlet createInboundRoot()
    {
        Context context = getContext();
        Engine.getInstance().getRegisteredConverters().add( new ZestConverter( objectFactory ) );

        if( qi4j.mode() == Application.Mode.development )
        {
            setDebugging( true );
        }
        router = new Router( context );

        router.attach( "/devices/create", newQi4jRestlet( CreationResource.class, Device.class ) );
        router.attach( "/devices/", newQi4jRestlet( EntityListResource.class, Device.class ) ).setName( "deviceskjhjhk" );
        router.attach( "/devices/{id}/", newQi4jRestlet( EntityResource.class, Device.class ) );
        router.attach( "/devices/{id}/{invoke}", newQi4jRestlet( EntityResource.class, Device.class ) );

        router.attach( "/", newQi4jRestlet( EntryPointResource.class, EntryPoint.class ) );

        ChallengeAuthenticator guard = new ChallengeAuthenticator( context, ChallengeScheme.HTTP_BASIC, "Storm Clouds" );
        SecurityService securityService = serviceFinder.findService( SecurityService.class ).get();
        guard.setVerifier( securityService );
        guard.setNext( router );
        guard.setEnroler( securityService );
        return guard;
    }

    private <K extends Identity, T extends ServerResource<K>> Restlet newQi4jRestlet( Class<T> resourceClass,
                                                                                      Class<K> entityClass
    )
    {

        @SuppressWarnings( "unchecked" )
        ResourceFactory<K, T> factory = objectFactory.newObject( DefaultResourceFactoryImpl.class,
                                                                 resourceClass, router
        );
        ZestConverter converter = new ZestConverter( objectFactory );
        return objectFactory.newObject( ZestEntityRestlet.class,
                                        factory,
                                        router,
                                        entityClass,
                                        converter
        );
    }
}
