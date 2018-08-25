package top.carljung.bill.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 *
 * @author wangchao
 */
public class Application extends ResourceConfig{
    private Application(){
    }
    
    public static Application getInstance(){
        Application app = new Application();
        app.packages("top.carljung.bill.jersey"
                    ,"top.carljung.bill.server.filters"
                    ,"top.carljung.bill.server.services");
        app.register(RolesAllowedDynamicFeature.class);
        return app;
    }
}
