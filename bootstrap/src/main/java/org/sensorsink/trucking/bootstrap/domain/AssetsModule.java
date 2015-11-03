package org.sensorsink.trucking.bootstrap.domain;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.library.restlet.assembly.RestletCrudModuleAssembler;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;
import org.sensorsink.model.devices.Device;
import org.sensorsink.model.devices.DeviceParameters;
import org.sensorsink.model.devices.Devices;

public class AssetsModule
    implements ModuleAssembler
{
    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new RestletCrudModuleAssembler( Devices.class ).assemble( module );
        new RestletCrudModuleAssembler( Device.class ).assemble( module );
        module.values( DeviceParameters.class ).visibleIn( Visibility.application );
        module.services( UuidIdentityGeneratorService.class );
        return module;
    }
}
