package ac.bali.sensorsink.hauler;

public interface Feeder
{
    void send( PointInfo info, String streamId, long timestamp, String value );
}
