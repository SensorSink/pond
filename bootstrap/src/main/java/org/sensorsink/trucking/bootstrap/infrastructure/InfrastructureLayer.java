package org.sensorsink.trucking.bootstrap.infrastructure;

import java.util.function.Function;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.LayerAssembler;
import org.apache.zest.bootstrap.layered.LayeredLayerAssembler;

public class InfrastructureLayer extends LayeredLayerAssembler
    implements LayerAssembler
{
    public static final String NAME = "Infrastructure Layer";
    private final ModuleAssembly configModule;
    private final Function<Application, Module> typeFinder;

    public InfrastructureLayer( ModuleAssembly configModule, Function<Application, Module> typeFinder )
    {
        this.configModule = configModule;
        this.typeFinder = typeFinder;
    }

    @Override
    public LayerAssembly assemble( LayerAssembly layer )
        throws AssemblyException
    {
        new StorageModule( configModule ).assemble( layer, layer.module( StorageModule.NAME ) );
        new IndexingModule( configModule ).assemble( layer, layer.module( IndexingModule.NAME ) );
        new SerializationModule( typeFinder ).assemble( layer, layer.module( SerializationModule.NAME ) );
        return layer;
    }
}
