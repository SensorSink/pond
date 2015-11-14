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

package org.sensorsink.pond.rest.rioprime.types;

import java.util.HashMap;

/**
 * {"data":
 * {
 * "INNE.indoor.output" : {"value":"31.9","get":{"method":"GET","path":"/control/points/INNE.indoor.output/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.output/"}},
 * "INNE.indoor.indoor" : {"value":"16.7","get":{"method":"GET","path":"/control/points/INNE.indoor.indoor/"},"put":{"method":"PUT","path":"/control/points/INNE.indoor.indoor/"}}
 * }
 * }
 */
public class RioPoints
{
    private HashMap<String,RioPoint> data;

    public RioPoints()
    {
        data = new HashMap<>();
    }

    public RioPoints( HashMap<String, RioPoint> data )
    {
        this.data = data;
    }

    public HashMap<String, RioPoint> getData()
    {
        return data;
    }

    public void setData( HashMap<String, RioPoint> data )
    {
        this.data = data;
    }
}
