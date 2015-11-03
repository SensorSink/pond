package org.sensorsink.model.devices;

import org.apache.zest.api.common.UseDefaults;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;

@Mixins( Device.CollectionMixin.class )
public interface Device extends Identity
{
    void startCollect();

    void stopCollect();

    interface State
    {
        @UseDefaults
        Property<Boolean> collecting();
    }

    abstract class CollectionMixin implements Device
    {
        @This
        private State state;

        @Override
        public void startCollect()
        {
            if( state.collecting().get() )
            {
                return;
            }
            state.collecting().set( true );
        }

        @Override
        public void stopCollect()
        {
            if( state.collecting().get() )
            {
                state.collecting().set( false );
            }
        }
    }
}
