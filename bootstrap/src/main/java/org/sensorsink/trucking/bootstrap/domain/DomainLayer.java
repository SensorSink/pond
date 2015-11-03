package org.sensorsink.trucking.bootstrap.domain;

import java.util.function.Function;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.layered.LayerAssembler;
import org.apache.zest.bootstrap.layered.LayeredLayerAssembler;

public class DomainLayer extends LayeredLayerAssembler
    implements LayerAssembler
{
    @Override
    public LayerAssembly assemble(LayerAssembly layer)
        throws AssemblyException
    {
        createModule( layer, AssetsModule.class );
        createModule( layer, CrudModule.class );
        createModule( layer, SchedulingModule.class );
        return layer;
    }

    public static Function<Application, Module> typeFinder()
    {
        return application -> application.findModule( "Domain Layer", "Assets Module" );
    }
}
