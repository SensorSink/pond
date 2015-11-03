package org.sensorsink.sink.elasticsearch;

import java.time.LocalDate;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.sensorsink.model.points.PointInfo;

public class ElasticSearchSink
{
    private static final String api_key = "c4b2203a-b9ef-36df-b651-8f2719967fc8";
    private static final String componentTemplateID = "b437b84f-9cb4-3274-afde-02f25f69f401";

    public static final String BASE_API = "http://localhost:9200/";

    public void send( PointInfo info, String streamId, long timestamp, String value )
    {
        String today = LocalDate.now().toString();
        final String url = BASE_API
                           + String.format( "%s/%s/%s",
                                            "timeseries-" + today,
                                            info.account().get() + "." +
                                            info.device().get().name().get(),
                                            streamId,
                                            "" + value,
                                            "" + timestamp
        );
        System.out.println( "URL: " + url );
        ClientResource grovestream = new ClientResource( url );
        grovestream.setMethod( Method.PUT );
        grovestream.setOnResponse( ( request, response ) -> {
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
        grovestream.handle();
    }
}
