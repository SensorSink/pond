package ac.bali.sensorsink.hauler.restlets;

import java.util.List;
import org.restlet.resource.ServerResource;

public class SensorListServerResource extends ServerResource
    implements SensorListResource
{
    @Override
    public List<SensorRef> retrieve()
    {
        return null;
    }
}
