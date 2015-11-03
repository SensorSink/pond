package ac.bali.sensorsink.hauler;

import ac.bali.sensorsink.hauler.destinations.sensorsink.CassandraClient;
import ac.bali.sensorsink.hauler.destinations.sensorsink.SensorSinkFeeder;
import org.sensorsink.model.rio.EdgeDTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main( String[] args )
        throws IOException
    {
        List<String> hosts = readHostnames();
        final Feeder feeder = createFeeder( args );
        hosts.parallelStream().forEach( hostname -> new HistoryRetriever( hostname, findUser(), findPassword(), point -> {
            EdgeDTO data = point.edge();
            PointInfo info = getPointMetaData(hostname);
            System.out.println( info );
            String fullEdgeName = point.fullname();
            String value = data.getValue();
            long timestamp = System.currentTimeMillis();
            feeder.send( info, fullEdgeName, timestamp, value );
        } ).readHistoryPoints() );
    }

    private static PointInfo getPointMetaData( String hostname )
    {
        // bali.io naming pattern for now.
        String[] parts = hostname.split( "\\." );
        String account = parts[4] + "." + parts[3];
        String city = parts[2];
        String component = parts[0];
        String organization = parts[1];
        return PointInfo.create( account, organization, capizalizeFirst( organization ), component, city );
    }

    private static String capizalizeFirst( String organization )
    {
        return Character.toUpperCase( organization.charAt( 0 ) ) + organization.substring( 1 );
    }

    private static String findUser()
    {
        // temporary implementation
        return System.getProperty( "user.name" );
    }

    private static String findPassword()
    {
        // temporary implementation
        return System.getProperty( "user.password" );
    }

    private static Feeder createFeeder( String[] args )
    {
        long now = System.currentTimeMillis();
//        return new GroveStreamFeeder( now );

        String[] parts = args[ 0 ].split( ":" );
        int port = 9042;
        if( parts.length > 1 )
        {
            port = Integer.parseInt( parts[ 1 ] );
        }
        CassandraClient client = new CassandraClient( parts[ 0 ], port );
        return new SensorSinkFeeder( client );
    }

    private static List<String> readHostnames()
        throws IOException
    {
        File hostfile = findHostFile();
        ArrayList<String> result = new ArrayList<>();
        try (FileReader file = new FileReader( hostfile ))
        {
            BufferedReader reader = new BufferedReader( file );
            reader.lines()
                .filter( line -> line != null )
                .map( String::trim )
                .filter( line -> line.length() > 0 )
                .forEach( result::add );
        }
        return result;
    }

    private static File findHostFile()
    {
        File curDirFile = new File( "hostnames" );
        if( curDirFile.exists() )
        {
            return curDirFile;
        }
        return new File( System.getProperty( "user.home" ) + "/.mangrove/hostnames" );
    }
}
