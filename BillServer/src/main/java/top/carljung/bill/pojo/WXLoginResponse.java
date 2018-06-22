package top.carljung.bill.pojo;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author wangchao
 */
public class WXLoginResponse {
    @SerializedName("openid")
    private String openId;
    @SerializedName("session_key")
    private String sessionKey;
    @SerializedName("unionid")
    private String unionId;
    @SerializedName("errcode")
    private String errorCode;
    @SerializedName("errmsg")
    private String errorMsg;
    
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
}
