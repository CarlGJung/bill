package top.carljung.bill.server.services;

import top.carljung.bill.db.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.http.server.Request;
import top.carljung.bill.config.BillProto;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.factory.SessionFactory;
import top.carljung.bill.pojo.WXLoginResponse;
import top.carljung.bill.server.Session;

/**
 *
 * @author wangchao
 */
@Path("wx")
public class WeChatServices {
    @Context SecurityContext context;
    
    @GET
    @Path("/login/{jsCode}")
    public Response login(@PathParam("jsCode") String jsCode, @Context Request req){
        BillProto.WX wxConfig = Configuration.instance.getWXConfig();
        String appId = wxConfig.getAppId();
        String secret = wxConfig.getSecret();
        
        Client client = ClientBuilder.newClient();
        WebTarget baseTarget = client.target("https://api.weixin.qq.com/sns/jscode2session");
        WebTarget fullTarget = baseTarget
                .queryParam("appid", appId)
                .queryParam("secret", secret)
                .queryParam("grant_type", "authorization_code")
                .queryParam("js_code", jsCode);
        Invocation.Builder invocation = fullTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocation.get();
        String loginRspStr = response.readEntity(String.class);
        Gson gson = new GsonBuilder().create();
        WXLoginResponse loginRsp = gson.fromJson(loginRspStr, WXLoginResponse.class);
        
        Response.Status status = null;
        NewCookie cookie = null;
        String rspMsg = "";
        User user = null;
        
        if (StringUtils.isNotBlank(loginRsp.getErrorCode()) && (user = User.ensureWXUser(loginRsp)) != null) {
            Session session = SessionFactory.instance().getSession(req);
            session.setAttribute("wx", loginRsp);
            cookie = new NewCookie("session", session.getId(), null, null, 1, null, Session.ALIVE_TIME_SECOND, null, false, false);
        } else {
            rspMsg = loginRspStr;
        }
        status = Response.Status.OK;
        
        return Response.status(status).cookie(cookie).entity(rspMsg).build();
    }
}
