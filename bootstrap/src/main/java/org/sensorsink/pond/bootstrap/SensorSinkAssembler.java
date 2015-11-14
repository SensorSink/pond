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

package org.sensorsink.pond.bootstrap;

import java.util.function.Function;
import org.apache.zest.api.structure.Application;
import org.apache.zest.api.structure.Module;
import org.apache.zest.bootstrap.ApplicationAssembly;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.LayeredApplicationAssembler;
import org.sensorsink.pond.bootstrap.connectivity.ConnectivityLayer;
import org.sensorsink.pond.bootstrap.domain.DomainLayer;
import org.sensorsink.pond.bootstrap.config.ConfigurationLayer;
import org.sensorsink.pond.bootstrap.infrastructure.InfrastructureLayer;

public class SensorSinkAssembler extends LayeredApplicationAssembler
{
    private static final String NAME = "SensorSink Pond";
    private static final String VERSION = "1.0.0.alpha";

    public SensorSinkAssembler( Application.Mode mode )
        throws AssemblyException
    {
        super( NAME, VERSION, mode );
    }

    @Override
    protected void assembleLayers( ApplicationAssembly assembly )
        throws AssemblyException
    {
        LayerAssembly configLayer = createLayer( ConfigurationLayer.class );
        ModuleAssembly configModule = assemblerOf( ConfigurationLayer.class ).configModule();
        LayerAssembly domainLayer = createLayer( DomainLayer.class );
        Function<Application, Module> typeFinder = DomainLayer.typeFinder();
        LayerAssembly infraLayer = new InfrastructureLayer( configModule, typeFinder ).assemble( assembly.layer( InfrastructureLayer.NAME ) );
        LayerAssembly connectivityLayer = createLayer( ConnectivityLayer.class );

        connectivityLayer.uses( domainLayer );
        domainLayer.uses( infraLayer );
        infraLayer.uses( configLayer );
    }

}
