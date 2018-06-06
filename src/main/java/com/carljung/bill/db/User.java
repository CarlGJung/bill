package com.carljung.bill.db;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import top.carljung.bill.pojo.WXLoginResponse;

/**
 *
 * @author wangchao
 */
@Table("user")
public class User extends Model{
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String EXTERNAL_ID = "external_id";
    public static final String DN = "dn";
    
    public int getUserId(){
        Integer id = getInteger(ID);
        return id == null ? 0 : id;
    }
    
    public void setName(String name){
        setString(NAME, name);
    }
    public String getName(){
        return getString(NAME);
    }
    public String getExternalId(){
        return getString(EXTERNAL_ID);
    }
    public void setExternalId(String externalId){
        setString(EXTERNAL_ID, externalId);
    }
    public String getDistinguishedName(){
        return getString(DN);
    }
    public void setDistinguishedName(String distinguishedName){
        setString(DN, distinguishedName);
    }
    public short getStatus(){
        return getShort(STATUS);
    }
    public void setStatus(short status){
        setShort(STATUS, status);
    }
    
    public static User ensureWXUser(WXLoginResponse wxLoginRsp){
        String wxDN = "dc=com,dc=qq,dc=weixin";
        User user = null;
        String openId = null;
        if (wxLoginRsp != null && StringUtils.isNoneBlank(openId = wxLoginRsp.getOpenId())) {
            user = User.findFirst("external_id = ? AND dn = ?", wxLoginRsp.getOpenId(), wxDN);
            if (user == null) {
                user = User.create("external_id", openId, "dn", wxDN);
            }
        }
        return user;
    }
}
