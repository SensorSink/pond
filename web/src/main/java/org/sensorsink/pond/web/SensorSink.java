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

package org.sensorsink.pond.web;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.zest.api.structure.Application;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.layered.LayeredApplicationAssembler;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;
import org.restlet.security.Enroler;
import org.restlet.security.Verifier;
import org.sensorsink.pond.bootstrap.SensorSinkAssembler;
import org.sensorsink.pond.bootstrap.connectivity.ConnectivityLayer;
import org.sensorsink.pond.bootstrap.connectivity.RestModule;
import org.sensorsink.pond.model.account.Account;
import org.sensorsink.pond.model.account.InitialDataService;
import org.sensorsink.pond.model.account.Role;
import org.sensorsink.pond.model.account.User;
import org.sensorsink.pond.model.account.UserRole;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.rest.common.AccountContext;
import org.sensorsink.pond.rest.common.SimpleEnroler;
import org.sensorsink.pond.rest.common.SimpleVerifier;

public class SensorSink extends ZrestApplication
{

    public SensorSink( Context context )
        throws AssemblyException
    {
        super( context );
    }

    @Override
    protected void addRoutes( Router router )
    {
        addResourcePath( "accounts", Account.class, "/" );
        addResourcePath( "devices", Device.class, "/accounts/{account}/", true, false );
        addResourcePath( "roles", UserRole.class, "/accounts/{account}/", true, false );
        addResourcePath( "roles", Role.class, "/system/", false, true );
        addResourcePath( "users", User.class, "/system/" );
        InitialDataService initialData = serviceFinder.findService( InitialDataService.class ).get();
        initialData.populateAccounts();
        initialData.populateRoles();
        initialData.populateUsers();
        printRoutes();
    }

    @Override
    protected LayeredApplicationAssembler createApplicationAssembler()
        throws AssemblyException
    {
        return new SensorSinkAssembler( Application.Mode.development );
    }

    @Override
    protected Filter createInnerInterceptor()
    {
        return newObject( AccountContext.class );
    }

    @Override
    protected Verifier createVerifier()
    {
        return newObject( SimpleVerifier.class );
    }

    @Override
    protected Enroler createEnroler()
    {
        return newObject( SimpleEnroler.class, this );
    }

    @Override
    protected String getConnectivityLayer()
    {
        return ConnectivityLayer.NAME;
    }

    @Override
    protected String getConnectivityModule()
    {
        return RestModule.NAME;
    }

    private <T> T newObject( Class<T> type, Object... uses )
    {
        try
        {
            T instamce = type.newInstance();
            objectFactory.injectTo( instamce, uses );
            return instamce;
        }
        catch( Exception e )
        {
            throw new UndeclaredThrowableException( e );
        }
    }
}
