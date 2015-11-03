package org.sensorsink.model.devices;

import java.util.List;
import org.apache.zest.api.property.Property;

public interface DeviceParameters
{
    Property<String> hostName();

    Property<List<String>> hostAliases();

}
