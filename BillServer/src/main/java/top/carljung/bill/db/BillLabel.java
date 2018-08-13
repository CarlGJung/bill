package top.carljung.bill.db;

import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import top.carljung.bill.proto.PBStore;

/**
 *
 * @author wangchao
 */
@Table("bill_labels")
public class BillLabel extends Model{
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String BILL_TYPE = "bill_type";
    public static final String NAME = "name";
    public static final String REMARK = "remark";
    
    public static final int ALL_USER = -1;
    
    public int getLabelId(){
        return getInteger(ID);
    }
    
    public int getUserId(){
        return getInteger(USER_ID);
    }
    
    public void setUserId(int userId){
        setInteger(USER_ID, userId);
    }
    
    public short getBillType(){
        return getShort(BILL_TYPE);
    }
    
    public void setBillType(int billType){
        setShort(BILL_TYPE, billType);
    }
    
    public String getName(){
        return getString(NAME);
    }
    
    public void setName(String name){
        setString(NAME, name);
    }
    
    public String getRemark(){
        return getString(REMARK);
    }
    
    public void setRemark(String remark){
        setString(REMARK, remark);
    }
    
    public PBStore.BillLabel.Builder toPBLabel(){
        PBStore.BillLabel.Builder pbLabel = PBStore.BillLabel.newBuilder();
        pbLabel.setId(this.getLabelId());
        pbLabel.setBillType(this.getBillType());
        pbLabel.setName(this.getName());
        pbLabel.setRemark(this.getRemark());
        return pbLabel;
    }
    
    public static PBStore.BillLabelList.Builder getLabels(int userId, int billType){
        PBStore.BillLabelList.Builder pbLabels = PBStore.BillLabelList.newBuilder();
        List<BillLabel> billLabels = BillLabel.find("(user_id = ? OR user_id = ? ) AND bill_type = ?", userId, ALL_USER, billType);
        
        for (BillLabel label : billLabels) {
            pbLabels.addLabels(label.toPBLabel());
        }
        return pbLabels;
    }
}
