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

package org.sensorsink.pond.model.scheduling;

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
    <T extends Identity> void bind( BindableTask<T> task, T entity );

    abstract class BindingMixin
        implements BindingSchedulingService
    {
        @Override
        public <T extends Identity> void bind( BindableTask<T> task, T entity )
        {
            task.entity().set( entity );
        }
    }
}
