package org.sensorsink.trucking.security;

import org.apache.zest.api.mixin.Mixins;
import org.restlet.security.Enroler;
import org.restlet.security.Verifier;

@Mixins( HardcodedSecurity.class )
public interface SecurityService extends Verifier, Enroler
{
}
