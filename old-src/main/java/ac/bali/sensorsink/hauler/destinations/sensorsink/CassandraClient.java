package ac.bali.sensorsink.hauler.destinations.sensorsink;

import ac.bali.sensorsink.hauler.PointInfo;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import java.util.HashSet;

public class CassandraClient
{
    private static final String KEYSPACE = "pond";
    private Cluster cluster;
    private Session session;
    private HashSet<String> tables = new HashSet<>();

    public CassandraClient( String node, int port )
    {
        cluster = Cluster.builder()
            .addContactPoint( node )
            .withClusterName( "SensorSink" )
            .withPort( port )
            .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf( "Connected to cluster: %s\n", metadata.getClusterName() );
        for( Host host : metadata.getAllHosts() )
        {
            System.out.printf( "Datacenter: %s; Host: %s; Rack: %s\n",
                               host.getDatacenter(), host.getAddress(), host.getRack() );
        }
        session = cluster.connect();
        session.execute( "CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE + " WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};" );
    }

    public void close()
    {
        cluster.close();
    }

    public void appendToTimeserie( final PointInfo info,
                                   final String streamId,
                                   final long time,
                                   final String value
    )
    {
        createTableIfNeeded( info );
        String tableName = tableName( info.account.name );
        String query = String.format( "INSERT INTO " + tableName + "(organization,component,stream,event_time,value)\n" +
                                      "VALUES ('%s','%s','%s',%d,'%s');",
                                      info.organization.id, info.component.name, streamId, time, value );
        session.execute( query );
    }

    public boolean doesKeyspaceExist( String keyspaceName )
    {
        return cluster.getMetadata().getKeyspace( keyspaceName ) != null;
    }

    public boolean doesTableExist( String keyspace, String tableName )
    {
        KeyspaceMetadata ks = cluster.getMetadata().getKeyspace( keyspace );
        return ks != null && ks.getTable( tableName ) == null;
    }

    private void createTableIfNeeded( PointInfo info )
    {
        String tableName = tableName( info.account.name );
        if( tables.contains( tableName ) )
        {
            return;
        }
        session.execute( "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" +
                         "organization text,\n" +
                         "component text,\n" +
                         "stream text,\n" +
                         "event_time timestamp,\n" +
                         "value text,\n" +
                         "PRIMARY KEY ((organization,component,stream),event_time)\n" +
                         ");"
        );
        tables.add( tableName );
    }

    private String tableName( String account )
    {
        return KEYSPACE + "." + account.replaceAll( "\\.", "_" ) + "_sensors";
    }
}
