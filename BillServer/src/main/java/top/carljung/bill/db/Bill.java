package top.carljung.bill.db;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import top.carljung.bill.proto.PBStore;

/**
 *
 * @author wangchao
 */
@Table("bills")
public class Bill extends Model{
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String LABEL_ID = "label_id";
    public static final String TYPE = "bill_type";
    public static final String MONEY = "money";
    public static final String STATE = "state";
    public static final String BILL_TIME = "bill_time";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY_MONTH = "day_month";//day of month
    public static final String WEEK_YEAR = "week_year";//week of year
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
    public long getTime(){
        return getLong(BILL_TIME);
    }
    public void setTime(long time){
        setLong(BILL_TIME, time);
        
        Calendar cal = new Calendar.Builder().setInstant(this.getTime()).build();
        setShort(YEAR, cal.get(Calendar.YEAR));
        setShort(MONTH, cal.get(Calendar.MONTH) + 1);//starts with 0
        setShort(DAY_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        setShort(WEEK_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
    }
    public short getYear(){
        return getShort(YEAR);
    }
    public short getMonth(){
        return getShort(MONTH);
    }
    public short getDayOfMonth(){
        return getShort(DAY_MONTH);
    }
    public short getWeekOfYear(){
        return getShort(WEEK_YEAR);
    }
    public String getDateStr(){
        return this.getYear() + "-" + this.getMonth() + "-" + this.getDayOfMonth();
    }
    public Date getCreatedAt(){
        return getDate(CREATED_AT);
    }
    public void setCreatedAt(Date createdAt){
        setDate(CREATED_AT, createdAt);
    }
    
    public PBStore.Bill.Builder toPBBill(){
        PBStore.Bill.Builder bill = PBStore.Bill.newBuilder();
        bill.setId(this.getBillId());
        bill.setTypeValue(this.getType());
        bill.setLabelId(this.getLabelId());
        bill.setMoney(this.getMoney());
        bill.setTime(this.getTime());
        return bill;
    }
    
    public static List<Bill> getBillList(int userId){
        return Bill.find("user_id = ? AND state = ? ORDER BY bill_time DESC", userId, PBStore.DataState.ACTIVED_VALUE);
    }
    
    public static Bill getActivedBill(int billId, int userId){
        return Bill.findFirst("id = ? AND user_id = ? AND state = ?", billId, userId, PBStore.DataState.ACTIVED_VALUE);
    }
    
    public static void deleteById(int id){
        Bill.update("state = ?", "id = ?", PBStore.DataState.DELETED_VALUE, id);
    }
}
