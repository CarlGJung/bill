package top.carljung.bill.pack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author wangchao
 */
public class FilePack {
    
    public static void sendFile(File webDir, String fileToPack, OutputStream out) throws FileNotFoundException, IOException{
        try(BufferedReader in = new BufferedReader(new FileReader(new File(webDir, fileToPack + ".txt")))){
            String filePath;
            byte[] buffer = new byte[8192];
            while((filePath = in.readLine()) != null){
                filePath = filePath.trim();
                
                if (filePath.length() > 0 && !filePath.startsWith("#")) {
                    File innerFile = new File(webDir, filePath);
                    writeFile(out, innerFile, buffer);
                }
            }
        } finally {
            out.close();
        }
    }
    
    private static void writeFile(final OutputStream out, final File file, final byte[] buffer) throws IOException{
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                File[] childFils = file.listFiles();
                for (File childFile : childFils) {
                    writeFile(out, childFile, buffer);
                }
            } else {
                try(BufferedInputStream innerStream = new BufferedInputStream(new FileInputStream(file))){
                    int bytesSize;
                    while ((bytesSize = innerStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesSize);
                    }
                }
            }
        }
    }
    
    public static boolean isPackedFile(String path){
        return StringUtils.isNotBlank(path) && getFileName(path).startsWith("packed-");
    }
    
    public static String getFileName(String path){
        if (StringUtils.isBlank(path)) {
            return path;
        }
        
        int separatorIndex = path.lastIndexOf("/");
        if (separatorIndex != -1) {
            return path.substring(separatorIndex + 1);
        }
        return path;
    }
}
