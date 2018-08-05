package top.carljung.bill.server.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.grizzly.http.server.Request;
import top.carljung.bill.db.Bill;
import top.carljung.bill.proto.StructureStore;
import top.carljung.bill.server.MediaType;
import top.carljung.bill.server.Session;

/**
 *
 * @author wangchao
 */
@Path("/bill")
public class BillServices {
    @Context
    SecurityContext securityContext;
    
    @POST
    @Path("/record")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public Response registerNewUser(StructureStore.Bill bill, @Context Request request){
        Session session = (Session)securityContext.getUserPrincipal();
        if (session != null) {
            Bill dbBill = Bill.create(Bill.USER_ID, session.getUserId());
            dbBill.setType(bill.getTypeValue());
            dbBill.setLabelId(bill.getLabelId());
            dbBill.setMoney(bill.getMoney());
            dbBill.saveIt();
        }
        return Response.ok().build();
    } 
}
