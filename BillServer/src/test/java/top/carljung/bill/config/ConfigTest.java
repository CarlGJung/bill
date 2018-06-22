package top.carljung.bill.config;


import com.googlecode.protobuf.format.JsonFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import top.carljung.bill.proto.ConfigStore;

/**
 *
 * @author wangchao
 */
public class ConfigTest {
    @Test
    public void testReadConfig(){
        try {
            JsonFormat jsonFormat = new JsonFormat();
            File configJson = new File("config/config.json");
            ConfigStore.Config.Builder config = ConfigStore.Config.newBuilder();
            jsonFormat.merge(new FileInputStream(configJson), config);
            config.getWx().getAppId();
        } catch (IOException ex) {
            Logger.getLogger(ConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
