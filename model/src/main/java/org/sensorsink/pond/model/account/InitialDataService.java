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
import org.apache.zest.api.concern.Concerns;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.unitofwork.NoSuchEntityException;
import org.apache.zest.api.unitofwork.UnitOfWorkFactory;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkConcern;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkPropagation;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;
import org.apache.zest.library.restlet.repository.CrudRepository;
import org.apache.zest.library.restlet.repository.RepositoryLocator;

@Mixins( InitialDataService.PrepopulateMixin.class )
@Concerns( UnitOfWorkConcern.class )
public interface InitialDataService
{

    String ROLE_ROOT = "root";
    String ROLE_SYSADMIN = "sysadmin";
    String ROLE_OPERATION = "operation";
    String ROLE_DEVOP = "devop";
    String ROLE_DEVELOPER = "developer";
    String ROLE_ACCOUNT_OWNER = "account_owner";
    String ROLE_ACCOUNT_OPERATOR = "account_operator";
    String ROLE_ACCOUNT_VIEWER = "account_viewer";
    String ROLE_ACCOUNT_INVITEE = "account_invitee";
    String ROLE_PUBLIC = "public";

    @UnitOfWorkPropagation( usecase = "Initial Data Population" )
    void populateAccounts();

    @UnitOfWorkPropagation( usecase = "Initial Data Population" )
    void populateRoles();

    @UnitOfWorkPropagation( usecase = "Initial Data Population" )
    void populateUsers();

    class PrepopulateMixin
        implements InitialDataService
    {
        @Structure
        private UnitOfWorkFactory uowf;

        @Structure
        private ValueBuilderFactory vbf;

        @Service
        private RepositoryLocator locator;

        @Override
        @UnitOfWorkPropagation( usecase = "Initial Data Population" )
        public void populateAccounts()
        {
            getOrCreateEntity( Account.class, "system" );
            getOrCreateEntity( Account.class, "bfm" );
        }

        @Override
        @UnitOfWorkPropagation( usecase = "Initial Data Population" )
        public void populateRoles()
        {
            getOrCreateEntity( Role.class, ROLE_SYSADMIN );
            getOrCreateEntity( Role.class, ROLE_OPERATION );
            getOrCreateEntity( Role.class, ROLE_DEVOP );
            getOrCreateEntity( Role.class, ROLE_DEVELOPER );
            getOrCreateEntity( Role.class, ROLE_ACCOUNT_OWNER );
            getOrCreateEntity( Role.class, ROLE_ACCOUNT_OPERATOR );
            getOrCreateEntity( Role.class, ROLE_ACCOUNT_VIEWER );
            getOrCreateEntity( Role.class, ROLE_ACCOUNT_INVITEE );
            getOrCreateEntity( Role.class, ROLE_PUBLIC );
        }

        @Override
        @UnitOfWorkPropagation( usecase = "Initial Data Population" )
        public void populateUsers()
        {
            Account systemAccount = getOrCreateEntity( Account.class, "system" );
            Account bfmAccount = getOrCreateEntity( Account.class, "bfm" );

            Role sysadmin = getOrCreateEntity( Role.class, ROLE_SYSADMIN );
            Role op = getOrCreateEntity( Role.class, ROLE_OPERATION );
            Role devop = getOrCreateEntity( Role.class, ROLE_DEVOP );
            Role dev = getOrCreateEntity( Role.class, ROLE_DEVELOPER );
            Role owner = getOrCreateEntity( Role.class, ROLE_ACCOUNT_OWNER );
            Role operator = getOrCreateEntity( Role.class, ROLE_ACCOUNT_OPERATOR );
            Role viewer = getOrCreateEntity( Role.class, ROLE_ACCOUNT_VIEWER );
            Role invitee = getOrCreateEntity( Role.class, ROLE_ACCOUNT_INVITEE );
            Role pub = getOrCreateEntity( Role.class, ROLE_PUBLIC );

            User rootUser = getOrCreateEntity( User.class, "root" );
            setup( rootUser, "SupremeInternetOfThings!", systemAccount, sysadmin, sysadmin, op, devop, dev, owner, operator, viewer, invitee, pub );
            User ops = getOrCreateEntity( User.class, "ops" );
            setup( ops, "SensorSinkRulez!!", systemAccount, op, devop, dev, owner, operator, viewer, invitee, pub );
            User niclas = getOrCreateEntity( User.class, "niclas" );
            setup( niclas, "bfm2679!!", bfmAccount, owner, operator, viewer, invitee, pub );
        }

        private void setup( User user,
                            String password,
                            Account account,
                            Role... roles
        )
        {
            ManyAssociation<Role> rolesAssoc = user.roles();
            for( Role role : roles )
            {
                rolesAssoc.add( role );
            }
            user.credentials().set( createCredentials( user, password ) );
            user.account().set( account );
        }

        private AccessCredentials createCredentials( User user, String password )
        {
            ValueBuilder<AccessCredentials> builder = vbf.newValueBuilder( AccessCredentials.class );
            builder.prototype().username().set( user.identity().get() );
            builder.prototype().password().set( password );
            return builder.newInstance();
        }

        private <T extends Identity> T getOrCreateEntity( Class<T> type, String identity )
        {
            CrudRepository<T> repository = locator.find( type );
            if( repository == null )
            {
                throw new IllegalArgumentException( "No repository defined for " + type );
            }
            try
            {
                return repository.get( identity );
            }
            catch( NoSuchEntityException e )
            {
                repository.create( identity );
                return repository.get( identity );
            }
        }
    }
}
