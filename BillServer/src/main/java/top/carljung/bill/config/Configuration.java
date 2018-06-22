package top.carljung.bill.config;

import com.googlecode.protobuf.format.JsonFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.carljung.bill.proto.ConfigStore;

/**
 *
 * @author wangchao
 */
public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    public static final Configuration instance = new Configuration();
    private ConfigStore.Config config;
    
    private Configuration(){
    }
    
    public ConfigStore.Config getConfig(){
        return config;
    }
    
    public ConfigStore.DB getDBConfig(){
        return getConfig().getDb();
    }
    
    public ConfigStore.WX getWXConfig(){
        return getConfig().getWx();
    }
    
    public ConfigStore.Server getServerConfig(){
        return getConfig().getServer();
    }
    
    public String getFileStorePath(){
        String storePath = getServerConfig().getStore();
        File store = new File(storePath);
        if (!store.exists()) {
            store.mkdir();
        }
        String subPath = "file/";
        String fileStorePath = storePath.endsWith("/") 
                ? (storePath + subPath)
                : (storePath + "/" + subPath);
        File fileStore = new File(fileStorePath);
        if (!fileStore.exists()) {
            fileStore.mkdir();
        }
        return  fileStorePath;
    }
    
    public void readConfig() throws FileNotFoundException, IOException{
        JsonFormat jsonFormat = new JsonFormat();
        File configJson = new File("config/config.json");
        ConfigStore.Config.Builder configBuilder = ConfigStore.Config.newBuilder();
        jsonFormat.merge(new FileInputStream(configJson), configBuilder);
        config = configBuilder.build();
    }
}
