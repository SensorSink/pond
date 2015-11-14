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

package org.sensorsink.pond.model.points;

import java.time.Instant;
import org.apache.zest.api.association.Association;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.property.Property;

public interface Point<T> extends Identity
{

    Property value();

    Property<Instant> time();

    Association<PointInfo> info();
}