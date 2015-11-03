package org.sensorsink.trucking.bootstrap.connectivity;

import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.layered.LayerAssembler;
import org.apache.zest.bootstrap.layered.LayeredLayerAssembler;

public class ConnectivityLayer extends LayeredLayerAssembler
    implements LayerAssembler
{
    public static String NAME;

    @Override
    public LayerAssembly assemble( LayerAssembly layer )
        throws AssemblyException
    {
        createModule( layer, RestModule.class );
        createModule( layer, SecurityModule.class );
        return layer;
    }
}
