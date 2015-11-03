package org.sensorsink.rest.rioprime;

import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.property.Property;

public interface RioHost extends Identity
{
    Property<String> entryPointUrl();

    Property<String> pointsUrl();

}
