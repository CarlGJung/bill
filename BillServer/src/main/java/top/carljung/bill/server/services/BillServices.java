package top.carljung.bill.server.services;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.grizzly.http.server.Request;
import top.carljung.bill.db.Bill;
import top.carljung.bill.db.BillLabel;
import top.carljung.bill.db.helper.BillHelper;
import top.carljung.bill.proto.PBStore;
import top.carljung.bill.server.MediaType;
import top.carljung.bill.server.Session;

/**
 *
 * @author wangchao
 */
@Path("/bills")
@RolesAllowed("user")
public class BillServices {
    @Context
    SecurityContext securityContext;
    @Inject
    Request request;
    
    @GET
    @Path("/bills")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public PBStore.BillDailyList getBills(){
        PBStore.BillDailyList bills;
        Session session = (Session)securityContext.getUserPrincipal();
        
        if (session != null) {
            bills = BillHelper.getBillDailyList(session.getUserId());
        } else {
            bills = PBStore.BillDailyList.getDefaultInstance();
        }
        return bills;
    } 
    
    @PUT
    @Path("/record")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public Response recordBill(PBStore.Bill bill){
        Session session = (Session)securityContext.getUserPrincipal();
        if (session != null) {
            Bill dbBill = Bill.create(Bill.USER_ID, session.getUserId());
            dbBill.setType(bill.getTypeValue());
            dbBill.setLabelId(bill.getLabelId());
            dbBill.setMoney(bill.getMoney());
            dbBill.setTime(bill.getTime());
            dbBill.saveIt();
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("/bills")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROTOBUF})
    public Response updateBills(PBStore.Bill bill){
        Session session = (Session)securityContext.getUserPrincipal();
        if (session != null && bill.getId() > 0) {
            Bill dbBill = Bill.getActivedBill(bill.getId(), session.getUserId());
            dbBill.setMoney(bill.getMoney());
            dbBill.setTime(bill.getTime());
            dbBill.setLabelId(bill.getLabelId());
            dbBill.setType(bill.getTypeValue());
            dbBill.saveIt();
        }
        
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBills(@PathParam("id") int id){
        Session session = (Session)securityContext.getUserPrincipal();
        if (session != null) {
            Bill.deleteById(id);
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
