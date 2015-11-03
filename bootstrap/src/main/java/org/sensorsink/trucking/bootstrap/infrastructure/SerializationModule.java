package org.sensorsink.trucking.bootstrap.infrastructure;

import java.util.function.Function;
import org.apache.zest.api.common.Visibility;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;
import org.apache.zest.valueserialization.jackson.JacksonValueSerializationAssembler;

public class SerializationModule
    implements ModuleAssembler
{
    public static final String NAME = "Serialization Module";
    private final Function<Application, Module> typeFinder;

    public SerializationModule( Function<Application, Module> typeFinder )
    {
        this.typeFinder = typeFinder;
    }

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new JacksonValueSerializationAssembler()
            .visibleIn( Visibility.layer )
            .withValuesModuleFinder( typeFinder )
            .assemble( module );
        module.services( UuidIdentityGeneratorService.class ).visibleIn( Visibility.layer );
        return module;
    }
}
