package ac.bali.sensorsink.hauler;

import org.sensorsink.model.rio.BlockDTO;
import org.sensorsink.model.rio.EdgeDTO;
import org.sensorsink.model.rio.EntryPointBookmark;
import org.sensorsink.model.rio.ProgramDetails;
import org.sensorsink.model.rio.ProgramsList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Status;

public class HistoryRetriever
{
    private final Reference base;
    private final String user;
    private final String password;
    private final Consumer<Point> callback;

    public HistoryRetriever( String hostname, String user, String password, Consumer<Point> callback )
    {
        this.callback = callback;
        base = new Reference( "http://" + hostname + ":8778/" );
        this.user = user;
        this.password = password;
    }

    public void readHistoryPoints()
    {
        RestLink link = RestLink.createLink( base, "control/", Method.GET );
        link.followLink( base, user, password, ( request, response ) -> {
            handleEntryPoint( response );
        } );
    }

    private void handleEntryPoint( Response resp )
    {
        if( resp.getStatus().equals( Status.SUCCESS_OK ) )
        {
            EntryPointBookmark ep = deserialize( EntryPointBookmark.class, resp );
            if( ep == null )
            {
                return;
            }
            RestLink programs = ep.getPrograms();
            programs.followLink( base, user, password, ( request, response ) -> handlePrograms( response ) );
        }
        else
        {
            System.err.println( "ERROR: " + resp.getStatus() );
        }
    }

    private void handlePrograms( Response resp )
    {
        ProgramsList list = deserialize( ProgramsList.class, resp );
        if( list == null )
        {
            return;
        }
        list.getPrograms()
            .stream()
            .forEach( programRef -> {
                          RestLink link = programRef.getLoad();
                          link.followLink( base, user, password, ( request, response ) ->
                                               handleProgram( response, programRef.getName() )
                          );
                      }
            );
    }

    private void handleProgram( Response resp, final String programName )
    {
        ProgramDetails program = deserialize( ProgramDetails.class, resp );
        if( program == null )
        {
            return;
        }
        program.getBlocks()
            .stream()
            .forEach( block -> handleBlock( programName, block ) );
    }

    private void handleBlock( String programName, BlockDTO block )
    {
        String blockName = programName + "." + block.getName() + ".";
        block.getEdges().stream()
            .filter( EdgeDTO::isTracked )
            .forEach( edge -> callback.accept( new Point( blockName + edge.getName(), edge ) ) );
    }

    private <T> T deserialize( Class<T> type, Response response )
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper().configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
            return mapper.readValue( response.getEntityAsText(), type );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }
}
