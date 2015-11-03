package ac.bali.sensorsink.hauler.restlets;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface HostResource
{
    @Get
    HostDetails retrieve();

    @Put
    void save( HostDetails host );

    @Delete
    void remove();

}
