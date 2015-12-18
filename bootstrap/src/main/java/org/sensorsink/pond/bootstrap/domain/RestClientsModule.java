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

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.sensorsink.pond.model.clients.ClientHandlerFactory;
import org.sensorsink.pond.rest.rioprime.RioPrimeClientHandler;
import org.sensorsink.pond.rest.rioprime.RioPrimeClientHandlerFactory;
import org.sensorsink.pond.rest.rioprime.RioPrimeClientHandlerMixin;

public class RestClientsModule
    implements ModuleAssembler
{
    public static final String NAME = "Rest Client Module";

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        module.services( ClientHandlerFactory.class)
            .withMixins( RioPrimeClientHandlerFactory.class )
            .taggedWith( "RIO_PRIME" ).visibleIn( Visibility.application );
        module.transients( RioPrimeClientHandler.class );
        module.objects( RioPrimeClientHandlerMixin.PointsResultHandler.class );
        return module;
    }
}
