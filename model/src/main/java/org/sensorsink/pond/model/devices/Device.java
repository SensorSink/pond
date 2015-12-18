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

import java.util.concurrent.ScheduledExecutorService;
import org.apache.zest.api.association.Association;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.common.UseDefaults;
import org.apache.zest.api.composite.TransientBuilderFactory;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.entity.Lifecycle;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;
import org.apache.zest.library.restlet.identity.IdentityManager;
import org.apache.zest.library.restlet.resource.CreationParameterized;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.account.Account;

@Mixins( { Device.DeviceMixin.class, DeviceLifecycleMixin.class, Device.DeviceCreationMixin.class } )
public interface Device extends Identity, Lifecycle, CreationParameterized<DeviceParameters>
{
    void startCollect();

    void stopCollect();

    void poll();

    String hostName();

    void modifyDevice( DeviceParameters parameters );

    @UseDefaults
    Property<Boolean> collecting();

    @UseDefaults
    Property<String> deviceType();

    @Optional
    Property<Integer> port();

    @Optional
    Property<AccessCredentials> credentials();

    @Optional
    Association<Account> account();

    abstract class DeviceMixin
        implements Device
    {

        @Structure
        ValueBuilderFactory vbf;

        @Structure
        TransientBuilderFactory tbf;

        @Service
        ScheduledExecutorService executor;

        @Service
        private IdentityManager identityManager;

        @Override
        public void startCollect()
        {
            if( collecting().get() )
            {
                return;
            }
            collecting().set( true );
        }

        @Override
        public void stopCollect()
        {
            if( collecting().get() )
            {
                collecting().set( false );
            }
        }

        @Override
        public String hostName()
        {
            return identityManager.extractName( identity().get() );
        }

        @Override
        public void poll()
        {
            DevicePollTask pollTask = tbf.newTransient( DevicePollTask.class, identity().get() );
            executor.submit( pollTask );
        }

        @Override
        public void modifyDevice( DeviceParameters parameters )
        {
            ValueBuilder<AccessCredentials> builder = vbf.newValueBuilder( AccessCredentials.class );
            builder.prototype().username().set( parameters.userName().get() );
            builder.prototype().password().set( parameters.password().get() );
            credentials().set( builder.newInstance() );
            port().set(parameters.port().get());
            deviceType().set( parameters.deviceType().get() );
        }
    }

    abstract class DeviceCreationMixin
        implements Device
    {
        @Override
        public Class<DeviceParameters> parametersType()
        {
            return DeviceParameters.class;
        }

        @Override
        public void parameterize( DeviceParameters parameters )
        {
            modifyDevice( parameters );
        }
    }
}
