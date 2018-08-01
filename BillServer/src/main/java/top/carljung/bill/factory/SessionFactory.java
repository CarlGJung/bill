package top.carljung.bill.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.ContainerRequestContext;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.Request;
import top.carljung.bill.db.User;
import top.carljung.bill.server.Session;
import top.carljung.bill.utils.Utils;

/**
 *
 * @author wangchao
 */
public class SessionFactory {
    public static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    public static volatile SessionFactory sessionFactory;
    
    private SessionFactory(){
    }
    
    public static SessionFactory instance(){
        //Double-Check idiom
        if (sessionFactory == null) {
            synchronized(SessionFactory.class){
                if (sessionFactory == null) {
                    sessionFactory = new SessionFactory();
                }
            }
        }
        
        return sessionFactory;
    }
    
    public Session createSession(User user){
        Session session =  new Session(generatorSessionId(), user.getUserId());
        sessions.put(session.getId(), session);
        return session;
    }
    
    public Session getSession(Request request){
        String sessionId = null;
        
        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            
            if ("cookie".equals(cookieName) || "session".equals(cookieName)) {
                sessionId = cookie.getValue();
            }
        }
        
        return sessions.get(sessionId);
    }
    
    public Session getSession(ContainerRequestContext crc){
        Map<String, javax.ws.rs.core.Cookie> cookies = crc.getCookies();
        String sessionId = null;
        javax.ws.rs.core.Cookie cookie = cookies.get("cookie");
        
        if (cookie != null) {
            sessionId = cookie.getValue();
        }
        
        if (StringUtils.isBlank(sessionId)) {
            cookie = cookies.get("session");
            if (cookie != null) {
                sessionId = cookie.getValue();
            }
        }
        return sessions.get(sessionId);
    }

    private String generatorSessionId(){
        String sessionId = Long.toString(System.currentTimeMillis()) + Double.toString(Math.random());
        return Utils.sha1Hex(sessionId);
    }
}
