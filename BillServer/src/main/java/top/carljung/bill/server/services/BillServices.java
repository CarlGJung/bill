package top.carljung.bill.server.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.grizzly.http.server.Request;
import top.carljung.bill.db.Bill;
import top.carljung.bill.db.BillLabel;
import top.carljung.bill.proto.PBStore;
import top.carljung.bill.server.MediaType;
import top.carljung.bill.server.Session;

/**
 *
 * @author wangchao
 */
@Path("/bills")
public class BillServices {
    @Context
    SecurityContext securityContext;
    @Inject
    Request request;
    
    @GET
    @Path("/bills")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public PBStore.BillList getBills(){
        PBStore.BillList.Builder bills;
        Session session = (Session)securityContext.getUserPrincipal();
        
        if (session != null) {
            bills = Bill.getBillList(session.getUserId());
        } else {
            bills = PBStore.BillList.newBuilder();
        }
        return bills.build();
    } 
    
    @POST
    @Path("/record")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public Response recordBill(PBStore.Bill bill){
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
    
    @GET
    @Path("/labels/{type}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public PBStore.BillLabelList getBillLabels(@PathParam("type") int type){
        Session session = (Session)securityContext.getUserPrincipal();
        if (session != null) {
            return BillLabel.getLabels(session.getUserId(), type).build();
        }
        
        return PBStore.BillLabelList.getDefaultInstance();
    }
}
