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

package org.sensorsink.pond.rest.rioprime;

import java.util.function.Consumer;
import org.apache.zest.api.composite.TransientBuilder;
import org.apache.zest.api.composite.TransientBuilderFactory;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.object.ObjectFactory;
import org.sensorsink.pond.model.clients.ClientHandler;
import org.sensorsink.pond.model.clients.ClientHandlerFactory;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.samples.Sample;

public class RioPrimeClientHandlerFactory
    implements ClientHandlerFactory
{
    @Structure
    private ObjectFactory objectFactory;

    @Structure
    private TransientBuilderFactory tbf;


    @Override
    public ClientHandler createClient( Device device, Consumer<Sample> callback )
    {
        TransientBuilder<RioPrimeClientHandler> builder = tbf.newTransientBuilder( RioPrimeClientHandler.class );
        RioPrimeClientHandler.State prototype = builder.prototypeFor( RioPrimeClientHandler.State.class );
        prototype.device().set( device );
        prototype.refreshOnNextPoll().set( true );
        prototype.callback().set( callback );
        RioPrimeClientHandler handler = builder.newInstance();
        handler.connect();
        return handler;
    }
}
