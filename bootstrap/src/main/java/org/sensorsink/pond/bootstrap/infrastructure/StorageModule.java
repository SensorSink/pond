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

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.entitystore.file.assembly.FileEntityStoreAssembler;
import org.apache.zest.entitystore.jdbm.assembly.JdbmEntityStoreAssembler;

public class StorageModule
    implements ModuleAssembler
{
    public static final String NAME = "Storage Module";
    private final ModuleAssembly configModule;

    public StorageModule( ModuleAssembly configModule )
    {
        this.configModule = configModule;
    }

    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new FileEntityStoreAssembler()
            .visibleIn( Visibility.application  )
            .withConfig( configModule, Visibility.application )
            .assemble( module );

//        new JdbmEntityStoreAssembler().assemble( module );
        return module;
    }
}
