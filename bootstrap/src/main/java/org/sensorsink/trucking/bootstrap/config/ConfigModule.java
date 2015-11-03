package org.sensorsink.trucking.bootstrap.config;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.entitystore.memory.MemoryEntityStoreService;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;
import org.apache.zest.valueserialization.jackson.JacksonValueSerializationAssembler;

public class ConfigModule
    implements ModuleAssembler
{
    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        module.services( MemoryEntityStoreService.class ).visibleIn( Visibility.layer );
        new JacksonValueSerializationAssembler().visibleIn( Visibility.layer ).assemble( module );
        module.services( UuidIdentityGeneratorService.class ).visibleIn( Visibility.layer );
        return module;
    }
}
