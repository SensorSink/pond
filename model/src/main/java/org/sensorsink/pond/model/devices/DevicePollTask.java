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

import java.util.List;
import java.util.function.Consumer;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.mixin.Initializable;
import org.apache.zest.api.mixin.InitializationException;
import org.apache.zest.api.mixin.Mixins;
import org.sensorsink.pond.model.clients.ClientFactory;
import org.sensorsink.pond.model.clients.ClientHandler;
import org.sensorsink.pond.model.points.Point;
import org.sensorsink.pond.model.scheduling.BindableTask;
import org.sensorsink.pond.model.sink.Sink;

@Mixins( DevicePollTask.DevicePollRunnable.class )
public interface DevicePollTask extends BindableTask<Device>
{
    abstract class DevicePollRunnable
        implements DevicePollTask, Initializable, Consumer<Point>
    {
        @Service
        private List<Sink> sinks;

        @Service
        private ClientFactory clientFactory;

        private ClientHandler client;

        @Override
        public void run()
        {
            System.out.println( "Polling " + entity().get().identity().get() );
            poll();
        }

        private void poll()
        {
            client.poll();
        }

        @Override
        public void initialize()
            throws InitializationException
        {
            client = clientFactory.createClient( entity().get(), this );
        }

        @Override
        public void accept( Point point )
        {
            sinks.forEach( sink -> sink.place( point ) );
        }
    }
}
