package org.sensorsink.sink.grovestream;

import java.time.Instant;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.sensorsink.model.points.Point;
import org.sensorsink.model.sink.Sink;

public class GroveStreamSink
    implements Sink
{
    private static final String api_key = "c4b2203a-b9ef-36df-b651-8f2719967fc8";
    private static final String componentTemplateID = "b437b84f-9cb4-3274-afde-02f25f69f401";

    public static final String BASE_API = "http://grovestreams.com/api/feed";

    @Override
    public void place( Point point, Instant timestamp )
    {
        String streamId = point.info().get().account().get().name().get();
        String componentName = point.info().get().name().get();
        String componentId = point.info().get().name().get();
        String value = String.valueOf( point.value().get() );
        final String url = BASE_API
                           + String.format( "?compName=%s&compTmplId=%s&api_key=%s&compId=%s&%s=%s&time=%s",
                                            componentName,
                                            componentTemplateID,
                                            api_key,
                                            componentId,
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

    @Override
    public boolean isSupporting( Class<?> type )
    {
        return Number.class.isAssignableFrom( type );
    }
}
