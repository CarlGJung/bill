package top.carljung.bill.handler;

import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wangchao
 */
public class ResourceHandler extends StaticHttpHandler{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ResourceHandler.class);
    public static final ResourceHandler instance = new ResourceHandler();
    
    private ResourceHandler(){
        super("/home/wangchao/MyProjects/Bill/bill-ng/public_html/");
        this.setFileCacheEnabled(false);
    }
   
    public boolean handleResource(String uri, Request request, Response response){
        try {
            return this.handle(uri, request, response);
        } catch (Exception ex) {
            logger.debug("handle Resource fail ", ex);
        }
        return false;
    }
}
