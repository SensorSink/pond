package ac.bali.sensorsink.hauler.restlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.ArrayList;
import java.util.List;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;

public class ListHosts extends Restlet
{

    @Override
    public void handle( Request request, Response response )
    {
        super.handle( request, response );

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query( "Host" );
        PreparedQuery pq = datastore.prepare( query );
        ArrayList<PullRegistration> result = new ArrayList<>();
        for( Entity entity : pq.asIterable() )
        {
            PullRegistration pr = new PullRegistration();
            pr.setType( (String) entity.getProperty( "type" ) );
            pr.setHost( (String) entity.getProperty( "host" ));
            pr.setPort( (String) entity.getProperty( "port" ));
            pr.setUser( (String) entity.getProperty( "user" ));
            result.add( pr );
        }
        response.setEntity( new JacksonRepresentation<List<PullRegistration>>( MediaType.APPLICATION_JSON, result ) );
    }
}
