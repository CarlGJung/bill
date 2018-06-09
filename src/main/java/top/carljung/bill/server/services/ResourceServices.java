package top.carljung.bill.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.slf4j.LoggerFactory;
import top.carljung.bill.handler.ResourceHandler;

/**
 *
 * @author wangchao
 */
@Path("")
public class ResourceServices{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ResourceServices.class);
    @GET
    public void index(@Context Request request, @Context Response response){
        ResourceHandler.instance.handleResource("index.html", request, response);
    }
}
