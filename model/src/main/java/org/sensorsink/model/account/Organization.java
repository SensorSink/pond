package org.sensorsink.model.account;

import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.property.Property;

public interface Organization extends Identity
{
    Property<String> name();

    Property<String> address1();

    Property<String> address2();

    Property<String> zipcode();

    Property<String> city();

    Property<String> country();
}
