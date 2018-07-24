package top.carljung.bill.utils;

import java.text.DecimalFormat;
import org.apache.commons.codec.digest.DigestUtils;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.proto.ConfigStore;

/**
 *
 * @author wangchao
 */
public class Utils {
    private static final float KB = 1024F;
    private static final float MB = 1024 * 1024F;
    private static final float GB = 1024 * 1024 * 1024F;
    private static final float TB = 1024 * 1024 * 1024F;
    private static final DecimalFormat formater = new DecimalFormat(".00");
    
    public static String sizeFormat(long size){
        if (size < KB) {
            return size + "byte";
        } else if (size < MB) {
            return formater.format(size / KB) + "KB";
        } else if (size < GB) {
            return formater.format(size / MB) + "MB";
        } else if (size < TB) {
            return formater.format(size / GB) + "GB";
        } else {
            return formater.format(size / TB) + "TB";
        }
    }
    
    public static String sha1Hex(String str){
        ConfigStore.Server serverConfig = Configuration.instance.getServerConfig();
        String salt = serverConfig.getSalt();
        return DigestUtils.sha1Hex(salt + str);
    }
}
