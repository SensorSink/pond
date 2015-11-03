package org.sensorsink.trucking.bootstrap.infrastructure;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.index.rdf.assembly.RdfNativeSesameStoreAssembler;
import org.apache.zest.library.rdf.repository.NativeConfiguration;

public class IndexingModule
    implements ModuleAssembler
{
    public static final String NAME = "Indexing Module";
    private final ModuleAssembly configModule;

    public IndexingModule( ModuleAssembly configModule )
    {
        this.configModule = configModule;
    }

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
//        new FileConfigurationAssembler().assemble( module );
//        new ESFilesystemIndexQueryAssembler()
//            .visibleIn( Visibility.application )
//            .withConfig( configModule, Visibility.application )
//            .assemble( module );

        configModule.entities( NativeConfiguration.class ).visibleIn( Visibility.application );
        new RdfNativeSesameStoreAssembler(Visibility.application, Visibility.module).assemble( module );
        return module;
    }
}
