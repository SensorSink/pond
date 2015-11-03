package ac.bali.sensorsink.hauler.restlets;

import ac.bali.sensorsink.RestLink;
import ac.bali.sensorsink.hauler.destinations.sensorsink.CassandraClient;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.ArrayList;
import java.util.List;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

public class HostListServerResource extends ServerResource
    implements HostListResource
{
    private Reference base;

    @Override
    public void init( Context context, Request request, Response response )
    {
        super.init( context, request, response );
        base = request.getResourceRef();
    }

    @Override
    public List<HostRef> retrieve()
    {
        ArrayList<HostRef> hosts = new ArrayList<>();

        Query query = new Query( "Host" );
        PreparedQuery pq = datastore.prepare(query);

        for( Entity entity : pq.asIterable() )
        {
            HostRef ref = new HostRef();
            String name = entity.getKey().getName();
            ref.setName( name );
            ref.setGetMethod( RestLink.createLink(base, "/" + name + "/", Method.GET) );
            ref.setPutMethod( RestLink.createLink( base, "/" + name + "/", Method.PUT ) );
            ref.setDeleteMethod( RestLink.createLink( base, "/" + name + "/", Method.DELETE ) );
            hosts.add( ref );
        }
        return hosts;
//        list.setHosts( hosts );
//        HostList list = new HostList();
//        return list;
    }
}
