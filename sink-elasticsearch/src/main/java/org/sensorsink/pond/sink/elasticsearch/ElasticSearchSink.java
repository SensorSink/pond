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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.spi.uuid.UuidIdentityGeneratorService;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.points.Point;
import org.sensorsink.pond.model.points.PointInfo;
import org.sensorsink.pond.model.sink.Sink;
import org.sensorsink.pond.rest.common.RestLink;

public class ElasticSearchSink
    implements Sink
{
    public static final Reference BASE_API = new Reference( "http://node1.bali.ac:9200/" );

    public static final String USER = "niclas";
    public static final String PASSWORD = "bfm2679";

    @Service
    private UuidIdentityGeneratorService uuid;

    @Override
    public void place( Point<?> point )
    {
        PointInfo pointInfo = point.info().get();
        Device deviceInfo = pointInfo.device().get();
        String deviceName = deviceInfo.identity().get();
        final String url = BASE_API
                           + String.format( "%s/%s/%s",
                                            pointInfo.device().get().account().get(),
                                            deviceName,
                                            uuid.generate( Point.class )
        );

        System.out.println( "URL: " + url );
        RestLink link = RestLink.createLink( BASE_API, url, Method.PUT );

        IndexValue content = new IndexValue( deviceName,
                                             pointInfo.name().get(),
                                             point.time().get(),
                                             point.value().toString()
        );

        link.followLinkWithContent( content, BASE_API, USER, PASSWORD, ( request, response ) -> {
            if( response.getStatus().equals( Status.SUCCESS_OK ) )
            {
                System.out.println( url + "  --> OK" );
                response.getHeaders().stream().forEach( System.out::println );
                System.out.println( response.getEntityAsText() );
            }
            else
            {
                System.err.println( "ERROR:" + response.getStatus() );
            }
        } );
    }

    @Override
    public boolean isSupporting( Class<?> type )
    {
        return false;
    }
}
