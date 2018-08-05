package top.carljung.bill.server.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import top.carljung.bill.db.User;
import top.carljung.bill.factory.SessionFactory;
import top.carljung.bill.proto.StructureStore;
import top.carljung.bill.server.MediaType;
import top.carljung.bill.server.Session;
import top.carljung.bill.utils.Utils;

/**
 *
 * @author wangchao
 */
@Path("/user")
public class UserSevices {
    
    @POST
    @Path("/register")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public Response registerNewUser(StructureStore.User user){
        String username = user.getUsername();
        String password = user.getPassword();
        
        if (StringUtils.isNoneBlank(username) && StringUtils.isNotBlank(password)) {
            if (!User.isUserExist(username)) {
                User newUser = User.create("name", username, "password", Utils.sha1Hex(password));
                newUser.saveIt();
                return Response.ok().build();
            }
        }
        return Response.serverError().build();
    }
    
    @POST
    @Path("/login")
    public Response login(StructureStore.User requestUser){
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        
        if (StringUtils.isNoneBlank(username) && StringUtils.isNotBlank(password)) {
            User user = User.getUser(username, password);
            if (user != null) {
                Session session = SessionFactory.instance().createSession(user);
                NewCookie cookie = new NewCookie("session", session.getId(), "/", null, null, 30 * 60, false);
                return Response.ok().cookie(cookie).build();
            }
        }
        return Response.serverError().build();
    }
}
