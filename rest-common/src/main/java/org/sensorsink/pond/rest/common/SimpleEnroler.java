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

package org.sensorsink.pond.rest.common;

import java.util.ArrayList;
import java.util.List;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.injection.scope.Uses;
import org.restlet.Application;
import org.restlet.data.ClientInfo;
import org.restlet.security.Enroler;
import org.restlet.security.Role;
import org.sensorsink.pond.model.account.SecurityRepository;

public class SimpleEnroler
    implements Enroler
{
    @Service
    private SecurityRepository repository;

    @Uses
    private Application application;

    @Override
    public void enrole( ClientInfo clientInfo )
    {
        org.restlet.security.User user = clientInfo.getUser();
        String name = user.getName();
        List<String> roleList = repository.findRoleNamesOfUser( name );
        List<Role> restletRoles = new ArrayList<>();
        roleList.stream().map( roleName -> Role.get( application, roleName ) );
        clientInfo.setRoles( restletRoles );
    }
}

