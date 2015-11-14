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

import org.apache.zest.api.association.Association;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.common.UseDefaults;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.entity.Lifecycle;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.account.Account;

@Mixins( { Device.CollectionMixin.class, DeviceLifecycleMixin.class } )
public interface Device extends Identity, Lifecycle
{
    void startCollect();

    void stopCollect();

    @UseDefaults
    Property<Boolean> collecting();

    @UseDefaults
    Property<String> deviceType();

    @UseDefaults
    Property<Integer> port();

    @Optional
    Property<AccessCredentials> credentials();

    @Optional
    Association<Account> account();

    abstract class CollectionMixin
        implements Device
    {
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
    }
}
