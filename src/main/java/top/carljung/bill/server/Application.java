package top.carljung.bill.server;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author wangchao
 */
@ApplicationPath("/")
public class Application extends ResourceConfig{
    public Application(){
        packages("top.carljung.bill.server.filters","top.carljung.bill.server.services");
    }
}
