package top.carljung.bill.server.probe;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wangchao
 */
public class AccessLoggerProbe extends HttpServerProbe.Adapter{
    public static final Logger logger = LoggerFactory.getLogger(AccessLoggerProbe.class);
    public static final String REQUEST_RECEIVE_TIME = AccessLoggerProbe.class.getName() + ".requestReceiveTime";
            
    @Override
    public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
        request.setAttribute(REQUEST_RECEIVE_TIME, System.currentTimeMillis());
    }
    
    @Override
    public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) {
        Long requestReceiveTime = (Long)response.getRequest().getAttribute(REQUEST_RECEIVE_TIME);
        long requestCompleteTime = System.currentTimeMillis();
        
        requestReceiveTime = requestReceiveTime == null
                        ?   requestCompleteTime
                        :   requestReceiveTime;
        long responseTime = requestCompleteTime - requestReceiveTime;
        logAccess(response, requestReceiveTime, responseTime);
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private void logAccess(Response response, long requestReceiveTime, long responseTime){
        try{
            String requestReceiveDate = dateFormat.format(new Date(requestReceiveTime));
            Request request = response.getRequest();
            String method = request.getMethod().getMethodString();
            String url = request.getRequestURL().toString();
            int status = response.getStatus();
            String remoteAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            logger.info(requestReceiveDate + " " + remoteAddr + " " + responseTime + "ms " + status + " " + method + " "+ url + " " + userAgent);
        }catch(Throwable e){
        }
    }
}
