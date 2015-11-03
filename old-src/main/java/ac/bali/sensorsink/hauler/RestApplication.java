package ac.bali.sensorsink.hauler;

import ac.bali.sensorsink.hauler.restlets.HostListServerResource;
import ac.bali.sensorsink.hauler.restlets.HostServerResource;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestApplication extends Application
{
    @Override
    public Restlet createInboundRoot()
    {

        Router router = new Router();
        router.attach( "/hosts/{id}/edit", HostServerResource.class );
        router.attach( "/hosts/{id}/view", HostServerResource.class );
        router.attach( "/hosts/{id}/", HostServerResource.class );
        router.attach( "/hosts", HostListServerResource.class );


        return new LogRestlet( router );
    }

    private class LogRestlet extends Restlet
    {
        private final Restlet next;

        public LogRestlet( Router router )
        {
            super( router.getContext() );
            next = router;
        }

        @Override
        public void handle( Request request, Response response )
        {
            super.handle( request, response );
            System.out.println( "   >>>   " + request.getEntityAsText() );
            next.handle( request, response );
            System.out.println( "   <<<   " + response.getEntityAsText() );
        }
    }
}
