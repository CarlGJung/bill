package top.carljung.bill.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import top.carljung.bill.server.Session;

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
    public Session getSession(HttpServletRequest request){
        String sessionId = null;
        
        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            
            if ("cookie".equals(cookieName) || "session".equals(cookieName)) {
                sessionId = cookie.getValue();
            }
        }
        
        return getSession(sessionId, true);
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
        return getSession(sessionId, false);
    }
    private Session getSession(String sessionId, boolean createIfNull) {
        Session session = sessions.get(sessionId);
        
        if (createIfNull && (session == null || !session.isAlive())) {
            sessionId = generatorSessionId();
            session = new Session(sessionId);
            sessions.put(sessionId, session);
        }
       
        return session;
    }

    private String generatorSessionId(){
        return generatorSessionId(null);
    }
    private String generatorSessionId(String sessionId){
        if (StringUtils.isBlank(sessionId)) {
            sessionId = String.valueOf(System.currentTimeMillis() + Math.random());
        }
        return DigestUtils.sha1Hex("carl-jung" + sessionId);
    }
}
