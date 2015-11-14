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

import java.util.List;
import java.util.function.Consumer;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.service.ServiceReference;
import org.apache.zest.api.service.qualifier.Tagged;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.points.Point;

@Mixins( ClientFactory.Mixin.class )
public interface ClientFactory
{
    ClientHandler createClient( Device device,
                                Consumer<Point> callback
    );

    class Mixin
        implements ClientFactory
    {
        @Service
        private List<ServiceReference<ClientHandlerFactory>> factories;

        public ClientHandler createClient( Device device,
                                           Consumer<Point> callback
        )
        {
            ClientHandlerFactory factory = factories.stream()
                .filter( ref -> hasTag( device.deviceType().get(), ref.metaInfo( Tagged.class ) ) )
                .map( ServiceReference::get ).findAny().get();
            return factory.createClient( device, callback );
        }

        private boolean hasTag( String name, Tagged tagged )
        {
            for( String tag : tagged.value() )
            {
                if( tag.equals( name ) )
                {
                    return true;
                }
            }
            return false;
        }
    }
}