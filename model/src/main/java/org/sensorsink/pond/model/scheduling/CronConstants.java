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

package org.sensorsink.pond.model.scheduling;

public interface CronConstants
{
    String EVERY_MINUTE = "0 * * * * *";
    String EVERY_5_MINUTE = "0 */5 * * * *";
    String EVERY_15_MINUTE = "0 */15 * * * *";
    String EVERY_HOUR = "0 0 * * * *";
    String EVERY_4_HOURS = "0 0 */4 * * *";
    String EVERY_8_HOURS = "0 0 */8 * * *";
    String EVERY_12_HOURS = "0 0 */12 * * *";
    String EVERY_DAY_AT_MIDNIGHT = "0 0 0 * * *";
    String EVERY_DAY_AT_4AM = "0 0 0 * * *";
    String EVERY_DAY_AT_NOON = "0 0 0 * * *";
}
