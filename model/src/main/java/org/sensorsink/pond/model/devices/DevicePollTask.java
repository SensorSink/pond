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

import java.util.function.Consumer;
import org.apache.zest.api.composite.TransientComposite;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.Uses;
import org.apache.zest.api.mixin.Initializable;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.service.ServiceReference;
import org.apache.zest.api.unitofwork.UnitOfWork;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkPropagation;
import org.sensorsink.pond.model.clients.ClientsFactory;
import org.sensorsink.pond.model.clients.ClientHandler;
import org.sensorsink.pond.model.samples.Sample;
import org.sensorsink.pond.model.sink.Sink;
import org.sensorsink.pond.model.support.UnitOfWorkTask;

@Mixins( DevicePollTask.DevicePollRunnable.class )
public interface DevicePollTask extends UnitOfWorkTask, TransientComposite
{
    abstract class DevicePollRunnable
        implements DevicePollTask, Initializable, Consumer<Sample>
    {
        @Service
        private Iterable<ServiceReference<Sink>> sinks;

        @Service
        private ClientsFactory clientsFactory;

        @Structure
        UnitOfWorkFactory uowf;

        @Uses
        private String deviceId;

        private ClientHandler client;

        @Override
        @UnitOfWorkPropagation(usecase = "Poll Device")
        public void execute()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            Device device = uow.get( Device.class, deviceId );
            Device deviceValue = uow.toValue( Device.class, device );
            client = clientsFactory.createClient( deviceValue, this );
            client.poll();
        }

        @Override

        public void accept( Sample point )
        {
            sinks.forEach( sink -> sink.get().place( point ) );
        }
    }
}
