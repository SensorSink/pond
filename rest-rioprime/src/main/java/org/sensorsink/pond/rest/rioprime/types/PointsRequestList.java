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

import java.io.Serializable;
import java.util.ArrayList;

public class PointsRequestList
    implements Serializable
{
    private ArrayList<String> points;

    public PointsRequestList()
    {
        points = new ArrayList<>();
    }

    public ArrayList<String> getPoints()
    {
        return points;
    }

    public void setPoints( ArrayList<String> points )
    {
        this.points = points;
    }

    public void addPoint( String point )
    {
        synchronized( this )
        {
            this.points.add( point );
        }
    }
}
