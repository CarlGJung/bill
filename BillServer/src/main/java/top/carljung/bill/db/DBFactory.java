package top.carljung.bill.db;

import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.proto.ConfigStore;

/**
 *
 * @author wangchao
 */
public class DBFactory{
    private static Map<String, String> properites = new HashMap<>();
    
    public static void init(){
        ConfigStore.DB dbConfig = Configuration.instance.getDBConfig();
        String[] urlSegements = dbConfig.getUrl().split("\\?");
        properites.put("driver", dbConfig.getDriver());
        properites.put("url", urlSegements[0]);
        
        String[] parameters = urlSegements[1].split("&");
        for (int i = 0; i < parameters.length; i++) {
            String[] kv = parameters[i].split("=");
            properites.put(kv[0], kv[1]);
        }
    }
    
    public static DB open(){
        return Base.open(properites.get("driver"), properites.get("url"), properites.get("user"), properites.get("password"));
    }
}
