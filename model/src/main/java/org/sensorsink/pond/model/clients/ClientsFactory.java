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

package org.sensorsink.pond.model.clients;

import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.service.ServiceReference;
import org.apache.zest.api.service.qualifier.ServiceTags;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.samples.Sample;

@Mixins( ClientsFactory.Mixin.class )
public interface ClientsFactory
{
    ClientHandler createClient( Device device, Consumer<Sample> callback );

    class Mixin
        implements ClientsFactory
    {
        @Service
        private Iterable<ServiceReference<ClientHandlerFactory>> factories;

        public ClientHandler createClient( Device device, Consumer<Sample> callback )
        {
            ClientHandlerFactory factory = StreamSupport.stream( factories.spliterator(), false )
                .filter( ref -> hasTag( device.deviceType().get(), ref.metaInfo( ServiceTags.class ) ) )
                .map( ServiceReference::get ).findAny().get();

            return factory.createClient( device, callback );
        }

        private boolean hasTag( String name, ServiceTags tags )
        {
            return tags.hasTag( name );
        }
    }
}