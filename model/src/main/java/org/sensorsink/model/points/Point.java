package org.sensorsink.model.points;

import org.apache.zest.api.association.Association;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.property.Property;
import org.sensorsink.model.devices.Device;

public interface Point extends Identity
{
    Property<String> name();

    Property<?> value();

    Association<PointInfo> info();
}
