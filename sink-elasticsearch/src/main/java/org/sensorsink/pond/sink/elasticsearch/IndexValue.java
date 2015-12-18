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

package org.sensorsink.pond.sink.elasticsearch;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class IndexValue
{
    private final String device;
    private final String point;
    private final String ts;
    private final Double value;

    public IndexValue( String device, String point, Instant ts, Double value )
    {
        this.device = device;
        this.point = point;
        this.ts = formatTimestamp( ts );
        this.value = value;
    }

    public String getDevice()
    {
        return device;
    }

    public String getPoint()
    {
        return point;
    }

    public String getTs()
    {
        return ts;
    }

    public Double getValue()
    {
        return value;
    }


    private String formatTimestamp( Instant timestamp )
    {
        String utc = ZonedDateTime.ofInstant( timestamp, ZoneId.of( "UTC" ) ).format( DateTimeFormatter.ISO_INSTANT );
        return utc;
    }

}
