package org.sensorsink.trucking.bootstrap.connectivity;

import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.library.restlet.assembly.RestletCrudConnectivityAssembler;
import org.apache.zest.library.restlet.resource.EntryPoint;

public class RestModule
    implements ModuleAssembler
{
    public static String NAME;

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new RestletCrudConnectivityAssembler().assemble( module );
        module.values( EntryPoint.class );
        return module;
    }
}
