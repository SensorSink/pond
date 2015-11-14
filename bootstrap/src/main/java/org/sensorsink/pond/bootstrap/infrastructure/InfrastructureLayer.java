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

package org.sensorsink.pond.bootstrap.infrastructure;

import java.util.function.Function;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.LayerAssembler;
import org.apache.zest.bootstrap.layered.LayeredLayerAssembler;

public class InfrastructureLayer extends LayeredLayerAssembler
    implements LayerAssembler
{
    public static final String NAME = "Infrastructure Layer";
    private final ModuleAssembly configModule;
    private final Function<Application, Module> typeFinder;

    public InfrastructureLayer( ModuleAssembly configModule, Function<Application, Module> typeFinder )
    {
        this.configModule = configModule;
        this.typeFinder = typeFinder;
    }

    @Override
    public LayerAssembly assemble( LayerAssembly layer )
        throws AssemblyException
    {
        new StorageModule( configModule ).assemble( layer, layer.module( StorageModule.NAME ) );
        new IndexingModule( configModule ).assemble( layer, layer.module( IndexingModule.NAME ) );
        new SerializationModule( typeFinder ).assemble( layer, layer.module( SerializationModule.NAME ) );
        new RestClientsModule().assemble( layer, layer.module( RestClientsModule.NAME ) );
        return layer;
    }
}
