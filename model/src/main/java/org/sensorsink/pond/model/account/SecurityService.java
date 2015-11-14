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

import java.util.List;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.mixin.Mixins;

@Mixins( {
    SecurityService.ContextMixin.class,
    SecurityService.RolesMixin.class
} )
public interface SecurityService
{
    boolean doesUserHaveRole( Role role );

    Account currentAccount();

    User currentUser();

    void setContext( Account account, User user );

    abstract class ContextMixin
        implements SecurityService
    {
        private ThreadLocal<User> currentUser = new ThreadLocal<>();
        private ThreadLocal<Account> currentAccount = new ThreadLocal<>();

        @Override
        public void setContext( Account account, User user )
        {
            currentAccount.set( account );
            currentUser.set( user );
        }

        @Override
        public Account currentAccount()
        {
            return currentAccount.get();
        }

        @Override
        public User currentUser()
        {
            return currentUser.get();
        }
    }

    abstract class RolesMixin
        implements SecurityService
    {
        @Service
        private SecurityRepository repository;

        @Override
        public boolean doesUserHaveRole( Role roleToCheck )
        {
            User currentUser = currentUser();
            List<Role> rolesOfUser = repository.findRolesOfUser( currentUser.identity().get() );
            return rolesOfUser.contains( roleToCheck );
        }
    }
}
