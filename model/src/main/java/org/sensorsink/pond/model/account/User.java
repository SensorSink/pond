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

import org.apache.zest.api.association.Association;
import org.apache.zest.api.association.ManyAssociation;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.injection.scope.Structure;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;
import org.apache.zest.api.value.ValueBuilder;
import org.apache.zest.api.value.ValueBuilderFactory;
import org.apache.zest.library.restlet.resource.CreationParameterized;

@Mixins( User.UserCreationMixin.class )
public interface User extends Identity, CreationParameterized<CreateUserParameters>
{
    @Optional
    Property<String> fullname();

    @Optional
    Property<AccessCredentials> credentials();

    @Optional
    Association<Account> account();

    ManyAssociation<Role> roles();

    abstract class UserCreationMixin
        implements User
    {
        @Structure
        private ValueBuilderFactory vbf;

        @Override
        public Class<CreateUserParameters> parametersType()
        {
            return CreateUserParameters.class;
        }

        @Override
        public void parameterize( CreateUserParameters parameters )
        {
            AccessCredentials creds = createCredentials(parameters.password().get());
            credentials().set( creds );
            fullname().set( parameters.fullName().get() );
        }

        private AccessCredentials createCredentials( String password )
        {
            ValueBuilder<AccessCredentials> builder = vbf.newValueBuilder( AccessCredentials.class );
            builder.prototype().username().set( identity().get() );
            builder.prototype().password().set( password );
            return builder.newInstance();
        }
    }
}
