/*
 * Copyright 2015 Niclas Hedhman, niclas@hedhman.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sensorsink.pond.bootstrap.domain;

import org.apache.zest.api.unitofwork.concern.UnitOfWorkConcern;
import org.apache.zest.bootstrap.Assemblers;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.EntityDeclaration;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.ServiceDeclaration;
import org.apache.zest.bootstrap.ValueDeclaration;
import org.apache.zest.library.scheduler.SchedulerConfiguration;
import org.apache.zest.library.scheduler.TaskRunner;
import org.apache.zest.library.scheduler.schedule.ScheduleFactory;
import org.apache.zest.library.scheduler.schedule.Schedules;
import org.apache.zest.library.scheduler.schedule.cron.CronSchedule;
import org.apache.zest.library.scheduler.schedule.once.OnceSchedule;
import org.apache.zest.library.scheduler.timeline.Timeline;
import org.apache.zest.library.scheduler.timeline.TimelineForScheduleConcern;
import org.apache.zest.library.scheduler.timeline.TimelineRecord;
import org.apache.zest.library.scheduler.timeline.TimelineScheduleMixin;
import org.apache.zest.library.scheduler.timeline.TimelineSchedulerServiceMixin;
import org.sensorsink.pond.model.scheduling.BindingSchedulingService;

public class BindingSchedulingAssembler
    extends Assemblers.VisibilityConfig<BindingSchedulingAssembler>
{
    private boolean timeline;

    /**
     * Activate the assembly of Timeline related services.
     *
     * @return SchedulerAssembler
     */
    public BindingSchedulingAssembler withTimeline()
    {
        timeline = true;
        return this;
    }

    @Override
    public void assemble( ModuleAssembly assembly )
        throws AssemblyException
    {
        assembly.services( ScheduleFactory.class );
        assembly.entities( Schedules.class );
        EntityDeclaration scheduleEntities = assembly.entities( CronSchedule.class, OnceSchedule.class );

        ValueDeclaration scheduleValues = assembly.values( CronSchedule.class, OnceSchedule.class );
        assembly.transients( Runnable.class ).withMixins( TaskRunner.class ).withConcerns( UnitOfWorkConcern.class );

        ServiceDeclaration schedulerDeclaration = assembly.services( BindingSchedulingService.class )
            .identifiedBy( BindingSchedulingService.class.getSimpleName() )
            .visibleIn( visibility() )
            .instantiateOnStartup();

        if( timeline )
        {
            scheduleEntities.withTypes( Timeline.class )
                .withMixins( TimelineScheduleMixin.class )
                .withConcerns( TimelineForScheduleConcern.class );

            scheduleValues.withTypes( Timeline.class )
                .withMixins( TimelineScheduleMixin.class )
                .withConcerns( TimelineForScheduleConcern.class );

            // Internal
            assembly.values( TimelineRecord.class );
            schedulerDeclaration.withTypes( Timeline.class ).withMixins( TimelineSchedulerServiceMixin.class );
        }

        if( hasConfig() )
        {
            configModule().entities( SchedulerConfiguration.class ).visibleIn( configVisibility() );
        }
    }
}
