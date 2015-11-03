package org.sensorsink.model.scheduling;

import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;
import org.apache.zest.api.unitofwork.UnitOfWork;
import org.apache.zest.api.unitofwork.UnitOfWorkCompletionException;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkRetry;
import org.apache.zest.library.scheduler.Task;

@Mixins( BinderTask.BinderRunnable.class )
public interface BinderTask extends Task, Identity
{
    Property<String> entityId();

    Property<String> taskId();

    abstract class BinderRunnable
        implements BinderTask
    {
        @Structure
        private UnitOfWorkFactory uowf;

        @Override
        @UnitOfWorkRetry( retries = 3 )
        public void run()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                Identity entity = uow.get( Identity.class, entityId().get() );
                BindableTask task = uow.get( BindableTask.class, taskId().get() );
                task.entity().set( entity );
                uow.complete();
            }
            catch( UnitOfWorkCompletionException e )
            {
                throw new TaskBindingException( "Unable to complete the unit of work.", e );
            }
        }
    }
}
