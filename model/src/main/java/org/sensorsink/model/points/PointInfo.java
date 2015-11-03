package org.sensorsink.model.points;

import org.apache.zest.api.association.Association;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.property.Property;
import org.sensorsink.model.account.Account;
import org.sensorsink.model.devices.DeviceInfo;

public interface PointInfo extends Identity
{
    Property<String> name();

    Association<DeviceInfo> device();

    Association<Account> account();
}
