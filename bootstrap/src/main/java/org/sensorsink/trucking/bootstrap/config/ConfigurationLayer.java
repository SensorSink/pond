package org.sensorsink.trucking.bootstrap.config;

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
