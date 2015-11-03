package org.sensorsink.rest.rioprime;

import org.apache.zest.api.entity.EntityBuilder;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.unitofwork.UnitOfWork;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;

@Mixins( RioHostsService.Mixin.class )
public interface RioHostsService
{
    void addHost( String hostname );

    void removeHost( String hostname );

    class Mixin implements RioHostsService
    {

        private UnitOfWorkFactory uowf;

        @Override
        public void addHost( String hostname )
        {
            EntityBuilder<RioHost> builder = uowf.currentUnitOfWork().newEntityBuilder( RioHost.class );
            builder.instance().identity().set( hostname );
            builder.newInstance();
        }

        @Override
        public void removeHost( String hostname )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            RioHost host = uow.get( RioHost.class, hostname );
            uow.remove( host );
        }
    }
}
