package top.carljung.bill.server.services;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import top.carljung.bill.config.Configuration;

/**
 *
 * @author wangchao
 */
@Path("")
public class ResourceServices{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ResourceServices.class);
    @GET
    public Response index(@Context Request request, @Context Response response){
        String docRoot = Configuration.instance.getServerConfig().getDocRoot();
        return Response.ok(new File(docRoot + "/index.html")).build();
    }
}
