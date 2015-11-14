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

package org.sensorsink.pond.bootstrap.connectivity;

import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.library.restlet.assembly.RestletCrudConnectivityAssembler;
import org.apache.zest.library.restlet.resource.EntryPoint;
import org.sensorsink.pond.rest.common.AccountContext;
import org.sensorsink.pond.rest.common.SimpleEnroler;
import org.sensorsink.pond.rest.common.SimpleVerifier;

public class RestModule
    implements ModuleAssembler
{
    public static String NAME;

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new RestletCrudConnectivityAssembler().assemble( module );
        module.values( EntryPoint.class );
        module.objects( AccountContext.class, SimpleVerifier.class, SimpleEnroler.class );
        return module;
    }
}
