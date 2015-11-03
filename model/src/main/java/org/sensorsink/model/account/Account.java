package org.sensorsink.model.account;

import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.property.Property;

public interface Account extends Identity
{
    Property<String> name();

    Property<String> ownerRealName();

    Property<Organization> organization();
}
