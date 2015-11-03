package org.sensorsink.trucking.bootstrap.connectivity;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.sensorsink.trucking.security.HardcodedSecurity;
import org.sensorsink.trucking.security.SecurityService;

public class SecurityModule
    implements ModuleAssembler
{
    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        module.services( SecurityService.class ).visibleIn( Visibility.application );
        module.services( SecurityService.class ).withMixins( HardcodedSecurity.class );
        return module;
    }
}
