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
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String ICON = "icon";
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
    
    public short getType(){
        return getShort(TYPE);
    }
    
    public void setType(int type){
        setShort(TYPE, type);
    }
    
    public String getName(){
        return getString(NAME);
    }
    
    public void setName(String name){
        setString(NAME, name);
    }
    
    public String getColor(){
        return getString(COLOR);
    }
    
    public void setColor(String color){
        setString(COLOR, color);
    }
    
    public String getIcon(){
        return getString(ICON);
    }
    
    public void setIcon(String icon){
        setString(ICON, icon);
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
        pbLabel.setTypeValue(this.getType());
        pbLabel.setName(this.getName());
        pbLabel.setColor(this.getColor());
        pbLabel.setIcon(this.getIcon());
        pbLabel.setRemark(this.getRemark());
        return pbLabel;
    }
    
    public static PBStore.BillLabelList.Builder getLabels(int userId, int type){
        PBStore.BillLabelList.Builder pbLabels = PBStore.BillLabelList.newBuilder();
        List<BillLabel> billLabels;
        if (type > PBStore.BillType.UNKNOW_BillType_VALUE ) {
            billLabels = BillLabel.find("(user_id = ? OR user_id = ? ) AND type = ?", userId, ALL_USER, type);
        } else {
            billLabels = BillLabel.find("(user_id = ? OR user_id = ? )", userId, ALL_USER);
        }
        
        for (BillLabel label : billLabels) {
            pbLabels.addLabels(label.toPBLabel());
        }
        return pbLabels;
    }
}
