package ac.bali.sensorsink.hauler.restlets;

import java.util.List;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface SensorResource
{
    @Get
    SensorDetails retrieve();

    @Delete
    void remove();

    @Put
    SensorDetails update();
}
