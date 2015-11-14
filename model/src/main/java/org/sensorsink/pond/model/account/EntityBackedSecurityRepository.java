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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.library.restlet.repository.RepositoryLocator;

public class EntityBackedSecurityRepository
    implements SecurityRepository
{

    @Service
    private RepositoryLocator locator;

    @Override
    public boolean verifyPassword( String userName, String password )
    {
        User user = null;
        try
        {
            user = locator.find( User.class ).get( userName );
        }
        catch( Exception e )
        {
            return false;
        }
        return user.credentials().get().verify(password);
    }

    @Override
    public List<Role> findRolesOfUser( String userName )
    {
        User user;
        try
        {
            user = locator.find( User.class ).get( userName );
        }
        catch( Exception e )
        {
            return Collections.emptyList();
        }
        return user.roles().toList();
    }

    @Override
    public List<String> findRoleNamesOfUser( String name )
    {
        return findRolesOfUser( name ).stream().map( role -> role.identity().get() ).collect( Collectors.toList() );
    }
}
