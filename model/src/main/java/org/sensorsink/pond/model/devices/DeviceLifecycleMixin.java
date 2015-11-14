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

package org.sensorsink.pond.model.devices;

import org.apache.zest.api.entity.EntityBuilder;
import org.apache.zest.api.entity.LifecycleException;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.sensorsink.pond.model.account.Account;
import org.sensorsink.pond.model.scheduling.BindableTask;
import org.sensorsink.pond.model.scheduling.BindingSchedulingService;
import org.sensorsink.pond.model.account.SecurityService;

public abstract class DeviceLifecycleMixin
    implements Device
{
    @Service
    private BindingSchedulingService scheduler;

    @This
    private Device me;

    @Structure
    private UnitOfWorkFactory uowf;

    @Service
    private SecurityService security;

    @Override
    public void create()
        throws LifecycleException
    {
        Account account = security.currentAccount();
        account().set(account);
        BindableTask<Device> task = createHostCheckTask();
        scheduler.scheduleCron( task, "15 * * * * *" );
        scheduler.bind( task, me );
    }

    @Override
    public void remove()
        throws LifecycleException
    {
    }

    private DevicePollTask createHostCheckTask()
    {
        EntityBuilder<DevicePollTask> builder = uowf.currentUnitOfWork().newEntityBuilder( DevicePollTask.class );
        DevicePollTask prototype = builder.instance();
        prototype.name().set( "Device Poll" );
        return builder.newInstance();
    }
}
