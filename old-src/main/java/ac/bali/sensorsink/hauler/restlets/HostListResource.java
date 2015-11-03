package ac.bali.sensorsink.hauler.restlets;

import java.util.List;
import org.restlet.resource.Get;

public interface HostListResource
{
    @Get
    List<HostRef> retrieve();
}
