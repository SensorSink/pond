package org.sensorsink.model.scheduling;

import org.apache.zest.api.entity.EntityBuilder;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.unitofwork.UnitOfWork;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.library.scheduler.SchedulerService;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;

@Mixins( BindingSchedulingService.BindingMixin.class )
public interface BindingSchedulingService extends SchedulerService
{
    void bind( BindableTask task, Identity entity );

    abstract class BindingMixin
        implements BindingSchedulingService
    {
        @Structure
        private UnitOfWorkFactory uowf;

        @Service
        UuidIdentityGeneratorService uuid;

        @Override
        public void bind( BindableTask task, Identity entity )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            EntityBuilder<BinderTask> builder = uow.newEntityBuilder( BinderTask.class );
            BinderTask instance = builder.instance();
            instance.entityId().set( entity.identity().get() );
            instance.taskId().set( task.identity().get() );
            instance.name().set( "Binding " + entity.identity().get() + " to " + task.name().get() );
            instance.identity().set( uuid.generate( BinderTask.class ) );
            BinderTask binder = builder.newInstance();
            scheduleOnce( binder, 1, true );
        }
    }
}
