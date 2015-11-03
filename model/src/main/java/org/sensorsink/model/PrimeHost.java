package org.sensorsink.model;

import org.apache.zest.api.property.Property;

public interface PrimeHost
{
    Property<String> hostName();

    Property<String> aliases();
}
