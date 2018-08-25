package top.carljung.bill.server.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import top.carljung.bill.config.Configuration;

/**
 *
 * @author wangchao
 */
@Path("/server")
@RolesAllowed("admin")
public class ServerServices {
    @GET
    @Path("/reload")
    public String reloadConfig(){
        return Configuration.instance.reloadConfig();
    }
}
