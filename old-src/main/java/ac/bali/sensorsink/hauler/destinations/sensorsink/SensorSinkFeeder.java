package ac.bali.sensorsink.hauler.destinations.sensorsink;

import ac.bali.sensorsink.hauler.Feeder;
import ac.bali.sensorsink.hauler.PointInfo;

public class SensorSinkFeeder
    implements Feeder
{
    private final CassandraClient client;

    public SensorSinkFeeder( CassandraClient client )
    {
        this.client = client;
    }

    @Override
    public void send( PointInfo info, String streamId, long timestamp, String value )
    {
        client.appendToTimeserie( info, streamId, timestamp, value );
    }
}
