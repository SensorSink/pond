package org.sensorsink.trucking.bootstrap.domain;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.library.scheduler.SchedulerService;
import org.apache.zest.library.scheduler.bootstrap.SchedulerAssembler;
import org.sensorsink.model.scheduling.BinderTask;
import org.sensorsink.model.scheduling.BindingSchedulingService;

public class SchedulingModule
    implements ModuleAssembler
{
    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new SchedulerAssembler()
            .withConfig( module, Visibility.module )
            .visibleIn( Visibility.layer )
            .assemble( module );
        module.services( SchedulerService.class ).withTypes( BindingSchedulingService.class );
        module.entities( BinderTask.class );
        return module;
    }
}
