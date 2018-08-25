package top.carljung.bill.server;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.db.User;

/**
 *
 * @author wangchao
 */
public class Session implements Principal, HttpSession{
    private Map<String, Object> attributes = new ConcurrentHashMap<>();
    private String sessionId;
    private long creationTime;
    private long touchTime;
    private int userId;
    private List<String> roles;
    
    public Session(String sessionId, int userId){
        this.touchTime = this.creationTime = System.currentTimeMillis();
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
    
    public boolean isAlive(){
        return touchTime - System.currentTimeMillis() >= Configuration.instance.getSessionTimeoutMillis(); 
    }
    
    public List<String> getRoles(){
        if (roles == null) {
            roles = new ArrayList<>();
            User user = User.findById(userId);
            if (user != null) {
                roles.add("user");
                if (user.isAdmin()) {
                    roles.add("admin");
                }
            }
        }
        
        return roles;
    }
    
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return sessionId;
    }

    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAttribute(String string) {
        return attributes.get(string);
    }

    @Override
    public Object getValue(String string) {
        return attributes.get(string);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String[] getValueNames() {
        return attributes.keySet().toArray(new String[attributes.size()]);
    }

    @Override
    public void setAttribute(String string, Object o) {
        attributes.put(string, o);
    }

    @Override
    public void putValue(String string, Object o) {
        attributes.put(string, o);
    }

    @Override
    public void removeAttribute(String string) {
        attributes.remove(string);
    }

    @Override
    public void removeValue(String string) {
        attributes.replace(string, null);
    }

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
