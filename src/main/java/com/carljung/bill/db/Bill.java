package com.carljung.bill.db;

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
    public static final String CURRENCY = "currency";
    public static final String CREATED_AT = "created_at";
    
    public int getBillId(){
        Integer id = getInteger(ID);
        return id == null ? 0 : id;
    }
    public void setCurrency(double currency){
        setDouble(CURRENCY, currency);
    }
    public double getCurrency(){
        return getDouble(CURRENCY);
    }
    public Date getCreatedAt(){
        return getDate(CREATED_AT);
    }
    public void setCreatedAt(Date createdAt){
        setDate(CREATED_AT, createdAt);
    }
}
