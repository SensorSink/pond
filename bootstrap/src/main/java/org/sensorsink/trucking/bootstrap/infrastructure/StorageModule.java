package org.sensorsink.trucking.bootstrap.infrastructure;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.entitystore.file.assembly.FileEntityStoreAssembler;

public class StorageModule
    implements ModuleAssembler
{
    public static final String NAME = "Storage Module";
    private final ModuleAssembly configModule;

    public StorageModule( ModuleAssembly configModule )
    {
        this.configModule = configModule;
    }

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new FileEntityStoreAssembler()
            .visibleIn( Visibility.application  )
            .withConfig( configModule, Visibility.application )
            .assemble( module );

        return module;
    }
}
