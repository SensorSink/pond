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
import org.restlet.security.SecretVerifier;
import org.restlet.security.Verifier;
import org.sensorsink.pond.model.account.SecurityRepository;

public class SimpleVerifier extends SecretVerifier
    implements Verifier
{
    @Service
    private SecurityRepository repository;

    @Override
    public int verify( String user, char[] secret )
    {
        if( user == null || secret == null )
        {
            return RESULT_UNKNOWN;
        }
        if( repository.verifyPassword( user, String.valueOf( secret ) ) )
        {
            return RESULT_VALID;
        }
        return RESULT_INVALID;
    }
}
