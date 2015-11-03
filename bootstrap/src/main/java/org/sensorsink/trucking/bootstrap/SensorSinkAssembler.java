package org.sensorsink.trucking.bootstrap;

import java.util.function.Function;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.ApplicationAssembly;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.LayeredApplicationAssembler;
import org.sensorsink.trucking.bootstrap.config.ConfigurationLayer;
import org.sensorsink.trucking.bootstrap.connectivity.ConnectivityLayer;
import org.sensorsink.trucking.bootstrap.domain.DomainLayer;
import org.sensorsink.trucking.bootstrap.infrastructure.InfrastructureLayer;

public class SensorSinkAssembler extends LayeredApplicationAssembler
{
    private static final String NAME = "SensorSink Hauler";
    private static final String VERSION = "1.0.0.alpha";

    public SensorSinkAssembler( Application.Mode mode )
        throws AssemblyException
    {
        super( NAME, VERSION, mode );
    }

    @Override
    protected void assembleLayers( ApplicationAssembly assembly )
        throws AssemblyException
    {
        LayerAssembly configLayer = createLayer( ConfigurationLayer.class );
        ModuleAssembly configModule = assemblerOf( ConfigurationLayer.class ).configModule();
        LayerAssembly domainLayer = createLayer( DomainLayer.class );
        Function<Application, Module> typeFinder = DomainLayer.typeFinder();
        LayerAssembly infraLayer = new InfrastructureLayer( configModule, typeFinder ).assemble( assembly.layer( InfrastructureLayer.NAME ) );
        LayerAssembly connectivityLayer = createLayer( ConnectivityLayer.class );

        connectivityLayer.uses( domainLayer );
        domainLayer.uses( infraLayer );
        infraLayer.uses( configLayer );
    }

}
