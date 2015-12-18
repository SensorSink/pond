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

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.zest.api.injection.scope.Service;
import org.apache.zest.api.mixin.Mixins;
import org.apache.zest.api.value.ValueSerialization;
import org.apache.zest.api.value.ValueSerializer;
import org.apache.zest.library.restlet.identity.IdentityManager;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.sensorsink.pond.model.devices.Device;
import org.sensorsink.pond.model.samples.Sample;
import org.sensorsink.pond.model.sink.Sink;
import org.sensorsink.pond.rest.common.RestLink;

@Mixins( ElasticSearchSink.Mixin.class )
public interface ElasticSearchSink extends Sink
{
    Reference BASE_API = new Reference( "http://node1.bali.ac:9200/" );

    String USER = "niclas";
    String PASSWORD = "bfm2679";

    class Mixin implements Sink
    {

        @Service
        ValueSerialization serialization;

        @Service
        private IdentityManager identityManager;

        @Override
        public void place( Sample sample )
        {
            Device deviceInfo = sample.device().get();
            String deviceName = identityManager.extractName( deviceInfo.identity().get() );
            ZonedDateTime now = ZonedDateTime.now();
            final String url = BASE_API
                               + String.format( "acc-%s-%s-%s/hist-%s/%s",
                                                sample.device().get().account().get(),
                                                now.getYear(),
                                                now.getMonthValue(),
                                                deviceName,
                                                sample.time().get()
            );

            RestLink link = RestLink.createLink( BASE_API, url, Method.PUT );
            ValueSerializer.Options options = new ValueSerializer.Options().withoutTypeInfo().withMapEntriesAsObjects();
            Map<String,Object> content = new HashMap<>();
            content.putAll( sample.values().get() );
            content.put("time", sample.time().get());
            content.put( "device", deviceName );

            link.followLinkWithContent( content, BASE_API, USER, PASSWORD, ( request, response ) -> {
                if( response.getStatus().isError() )
                {
                    System.err.println( "ERROR:" + response.getStatus() );
                }
                else
                {
                    response.getHeaders().stream().forEach( System.out::println );
                    System.out.println( response.getEntityAsText() );
                }
            } );
        }

        @Override
        public boolean isSupporting( Class<?> type )
        {
            return false;
        }
    }
}
