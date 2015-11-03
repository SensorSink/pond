package ac.bali.sensorsink.hauler.restlets;

import com.google.appengine.api.datastore.KeyFactory;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ServerResource;

public class SensorServerResource extends ServerResource
    implements SensorResource
{
    private Key key;
    private DatastoreService datastore;

    @Override
    public void init( Context context, Request request, Response response )
    {
        super.init( context, request, response );
        String id = (String) request.getAttributes().get( "id" );
        key = KeyFactory.createKey( "Sensor", id );
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    public SensorDetails retrieve()
        throws EntityNotFoundException
    {
        Entity entity = datastore.get( key );
        return null;
    }

    @Override
    public void remove()
    {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.delete( key );
    }

    @Override
    public SensorDetails update()
    {
        return null;
    }
}
