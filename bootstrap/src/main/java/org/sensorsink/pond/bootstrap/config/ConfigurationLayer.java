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

package org.sensorsink.pond.bootstrap.config;

import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.LayerAssembler;
import org.apache.zest.bootstrap.layered.LayeredLayerAssembler;

public class ConfigurationLayer extends LayeredLayerAssembler
    implements LayerAssembler
{
    public static final String NAME = "Configuration Layer";
    private ModuleAssembly configModule;

    @Override
    public LayerAssembly assemble( LayerAssembly layer )
        throws AssemblyException
    {
        configModule = createModule( layer, ConfigModule.class );
        return layer;
    }

    public ModuleAssembly configModule()
    {
        return configModule;
    }
}
