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
import org.apache.zest.api.concern.Concerns;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkConcern;
import org.apache.zest.api.unitofwork.concern.UnitOfWorkPropagation;

@Concerns( UnitOfWorkConcern.class )
public interface SecurityRepository
{
    @UnitOfWorkPropagation
    boolean verifyPassword( String user, String password );

    @UnitOfWorkPropagation
    List<Role> findRolesOfUser( String user );

    @UnitOfWorkPropagation
    List<String> findRoleNamesOfUser( String name );
}
