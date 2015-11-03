package ac.bali.sensorsink.hauler.restlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

public class HostServerResource extends ServerResource
    implements HostResource
{
    private Key key;
    private DatastoreService datastore;

    @Override
    public void init( Context context, Request request, Response response )
    {
        super.init( context, request, response );
        String name = (String) request.getAttributes().get( "id" );
        key = KeyFactory.createKey( "Host", name );
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    public HostDetails retrieve()
    {
        try
        {
            Entity entity = datastore.get( key );
            return HostDetails.createFromEntity( entity );
        }
        catch( EntityNotFoundException e )
        {
            throw new org.restlet.resource.ResourceException( Status.CLIENT_ERROR_NOT_FOUND );
        }
    }

    @Override
    public void save( HostDetails host )
    {
        Entity entity = host.toEntity( key );
        datastore.put( entity );
    }

    @Override
    public void remove()
    {
        datastore.delete( key );
    }
}
