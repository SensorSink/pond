/*
 * Copyright 2015 Niclas Hedhman, niclas@hedhman.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sensorsink.pond.model.account;

import org.apache.zest.api.association.ManyAssociation;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.entity.EntityBuilder;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.entity.Lifecycle;
import org.apache.zest.api.entity.LifecycleException;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.injection.scope.This;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;

@Mixins( { Account.StateMixin.class } )
public interface Account extends Identity
{
    @Optional
    Property<Organization> organization();

    @Optional
    ManyAssociation<User> users();

    void modifyOrganization( OrganizationParameters params );

    abstract class StateMixin
        implements Account
    {
        @Structure
        private ValueBuilderFactory vbf;

        public void modifyOrganization( OrganizationParameters params )
        {
            ValueBuilder<Organization> builder = vbf.newValueBuilder( Organization.class );
            Organization p = builder.prototype();
            p.name().set( params.organizationName().get() );
            p.address1().set( params.address1().get() );
            p.address2().set( params.address2().get() );
            p.zipcode().set( params.zipcode().get() );
            p.city().set( params.city().get() );
            p.country().set( params.country().get() );
            Organization org = builder.newInstance();
            organization().set( org );
        }
    }
}
