package top.carljung.bill.config;

import com.googlecode.protobuf.format.JsonFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.carljung.bill.db.DBFactory;
import top.carljung.bill.db.LiquibaseInit;
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
    
    private File webDir = null;
    
    public File getWebDir(){
        return webDir;
    }
    
    public void loadConfig() throws IOException{
        loadConfig(readConfig());
    }
    
    public synchronized String reloadConfig(){
        String result = "Fail!";
        File backupFile = null;
        
        try {
            backupFile = backupConfig();
            if (backupFile != null) {
                ConfigStore.Config.Builder newConfig = readConfig();
                new LiquibaseInit().init(newConfig.getDb());
                DBFactory.init(newConfig.getDb());
                loadConfig(newConfig);
                result = "Success!";
                logger.info("reload config success: " + new JsonFormat().printToString(config));
            }
        } catch (IOException ex){
            logger.warn("read new config.json fail",  ex);
        } catch ( LiquibaseException | SQLException ex) {
            logger.warn("reload config fail, roll back to old config: {}.", backupFile.getName(), ex);
            
            try {
                new LiquibaseInit().init(getDBConfig());
                DBFactory.init(getDBConfig());
                result = "Roll back!";
            } catch (LiquibaseException | SQLException ex1) {
                logger.error("roll back to old config {} fail, system will not work properly!", backupFile.getName(), ex);
                result = "Fatal!";
            }
        }
        return result;
    }
    
    private void loadConfig(ConfigStore.Config.Builder configBuilder){
        config = configBuilder.build();
        webDir = new File(getServerConfig().getDocRoot());
    }
    
    private ConfigStore.Config.Builder readConfig() throws FileNotFoundException, IOException{
        return readConfig(new File("config/config.json"));
    }
    
    private ConfigStore.Config.Builder readConfig(File configFile) throws FileNotFoundException, IOException{
        JsonFormat jsonFormat = new JsonFormat();
        ConfigStore.Config.Builder configBuilder = ConfigStore.Config.newBuilder();
        jsonFormat.merge(new FileInputStream(configFile), configBuilder);
        return configBuilder;
    }
    
    private File backupConfig() {
        JsonFormat jsonFormat = new JsonFormat();
        File backupFile = new File("config/config.json." + System.currentTimeMillis());
        
        try (OutputStream out = new FileOutputStream(backupFile);) {
            jsonFormat.print(config, out);
        } catch (IOException ex) {
            backupFile = null;
        }
        return backupFile;
    }
}
