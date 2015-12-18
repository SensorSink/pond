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

package org.sensorsink.pond.bootstrap.domain;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.LayerAssembly;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.bootstrap.layered.ModuleAssembler;
import org.apache.zest.library.restlet.assembly.RestletCrudModuleAssembler;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;
import org.sensorsink.pond.model.account.AccessCredentials;
import org.sensorsink.pond.model.account.Account;
import org.sensorsink.pond.model.account.InitialDataService;
import org.sensorsink.pond.model.account.Organization;
import org.sensorsink.pond.model.account.OrganizationParameters;
import org.sensorsink.pond.model.account.User;
import org.sensorsink.pond.model.account.CreateUserParameters;
import org.sensorsink.pond.model.account.EntityBackedSecurityRepository;
import org.sensorsink.pond.model.account.Role;
import org.sensorsink.pond.model.account.SecurityRepository;
import org.sensorsink.pond.model.account.SecurityService;
import org.sensorsink.pond.model.account.UserRole;

public class AccountsModule
    implements ModuleAssembler
{
    @Override
    public ModuleAssembly assemble( LayerAssembly layer, ModuleAssembly module )
        throws AssemblyException
    {
        new RestletCrudModuleAssembler( Account.class ).assemble( module );
        new RestletCrudModuleAssembler( User.class ).assemble( module );
        new RestletCrudModuleAssembler( Role.class ).assemble( module );
        new RestletCrudModuleAssembler( UserRole.class ).assemble( module );

        module.values( Organization.class ).visibleIn( Visibility.layer );
        module.values( OrganizationParameters.class ).visibleIn( Visibility.application );

        module.values( AccessCredentials.class ).visibleIn( Visibility.layer );
        module.values( CreateUserParameters.class ).visibleIn( Visibility.layer );

        module.services( UuidIdentityGeneratorService.class );
        module.services( SecurityRepository.class )
            .withMixins( EntityBackedSecurityRepository.class )
            .visibleIn( Visibility.application );
        module.services( SecurityService.class ).visibleIn( Visibility.application );
        module.services( InitialDataService.class ).visibleIn( Visibility.application );

        return module;
    }
}
