package top.carljung.bill.server.filters;

import java.io.IOException;
import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import top.carljung.bill.factory.SessionFactory;

/**
 *
 * @author wangchao
 */
@Provider
@PreMatching
public class SecurityRequestFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        crc.setSecurityContext(new SecurityContext(){
            @Override
            public Principal getUserPrincipal() {
                return SessionFactory.instance().getSession(crc);
            }

            @Override
            public boolean isUserInRole(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isSecure() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAuthenticationScheme() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

}
