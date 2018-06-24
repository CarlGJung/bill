package top.carljung.bill.server.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author wangchao
 */
@Provider
public class OriginFilter implements ContainerResponseFilter{

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String requestOrigin = requestContext.getHeaderString("origin");
        //TO DO check if origin is allowed
        responseContext.getHeaders().add("Access-Control-Allow-Origin", requestOrigin);
    }
    
}
