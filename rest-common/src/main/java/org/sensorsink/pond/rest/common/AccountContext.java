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

import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.library.restlet.repository.RepositoryLocator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;
import org.sensorsink.pond.model.account.Account;
import org.sensorsink.pond.model.account.User;
import org.sensorsink.pond.model.account.SecurityService;

public class AccountContext extends Filter
{
    @Service
    private RepositoryLocator locator;

    @Service
    private SecurityService security;

    @Override
    protected int beforeHandle( Request request, Response response )
    {
        String username = request.getClientInfo().getUser().getName();
        User user = locator.find( User.class ).get( username );
        Account account = user.account().get();
        security.setContext( account, user );
        return CONTINUE;
    }

    @Override
    protected void afterHandle( Request request, Response response )
    {
        security.setContext( null, null );
    }
}
