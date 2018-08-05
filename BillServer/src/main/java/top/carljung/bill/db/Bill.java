package top.carljung.bill.db;

import java.sql.Date;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author wangchao
 */
@Table("bill")
public class Bill extends Model{
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String LABEL_ID = "label_id";
    public static final String TYPE = "type";
    public static final String MONEY = "money";
    public static final String CREATED_AT = "created_at";
    
    public int getBillId(){
        return getInteger(ID);
    }
    public void setUserId(int userId){
        setInteger(USER_ID, userId);
    }
    public int getUserId(){
        return getInteger(USER_ID);
    }
    public void setLabelId(int labelId){
        setInteger(LABEL_ID, labelId);
    }
    public int getLabelId(){
        return getInteger(LABEL_ID);
    }
    public void setType(int type){
        setShort(TYPE, type);
    }
    public short getType(){
        return getShort(TYPE);
    }
    public void setMoney(double money){
        setDouble(MONEY, money);
    }
    public double getMoney(){
        return getDouble(MONEY);
    }
    public Date getCreatedAt(){
        return getDate(CREATED_AT);
    }
    public void setCreatedAt(Date createdAt){
        setDate(CREATED_AT, createdAt);
    }
}
