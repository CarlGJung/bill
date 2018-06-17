package top.carljung.bill.server.handler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.NIOInputStream;
import org.glassfish.grizzly.http.multipart.ContentDisposition;
import org.glassfish.grizzly.http.multipart.MultipartEntry;
import org.glassfish.grizzly.http.multipart.MultipartEntryHandler;
import org.glassfish.grizzly.http.multipart.MultipartScanner;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.utils.Utils;

/**
 *
 * @author wangchao
 */
public class UploadHandler extends HttpHandler{
    private static final Logger logger = LoggerFactory.getLogger(UploadHandler.class);
    
    @Override
    public void service(Request request, Response response) throws Exception {
        response.suspend();
        UploadMultipartHander uploader = new UploadMultipartHander();
        MultipartScanner.scan(request, uploader, new EmptyCompletionHandler<Request>(){
            @Override
            public void completed(final Request request) {
                logger.info("upload file " + uploader.getFilename() + " "
                        + Utils.sizeFormat(uploader.getUploadedSize()) + " completed");
                try{
                    response.resume();
                }catch(Throwable ex){
                }
            }
            
            @Override
            public void failed(Throwable throwable) {
                logger.warn("upload file " + uploader.getFilename() + " fail", throwable);
                try{
                    response.resume();
                }catch(Throwable ex){
                }
            }
        });
    }
    
    private static final class UploadMultipartHander implements MultipartEntryHandler{
        private static final String FILE_ENTRY = "file";
        private String filename;
        private final AtomicLong uploadedBytesCounter = new AtomicLong();
        
        @Override
        public void handle(MultipartEntry multipartEntry) throws Exception {
            ContentDisposition contentDisposition = multipartEntry.getContentDisposition();
            String entry = contentDisposition.getDispositionParamUnquoted("name");
            
            if (FILE_ENTRY.equals(entry)) {
                String filename = contentDisposition.getDispositionParamUnquoted("filename");
                this.filename = filename;
                logger.info("start upload file " + filename);
                filename = Configuration.instance.getFileStorePath() + filename;
                NIOInputStream inputStream = multipartEntry.getNIOInputStream();
                inputStream.notifyAvailable(new UploadReadHandler(inputStream, filename, uploadedBytesCounter));
            } else {
                multipartEntry.skip();
                logger.info("unknow multipart entry " + entry);
            }
        }
        
        public String getFilename(){
            return filename;
        }
        
        public long getUploadedSize(){
            return uploadedBytesCounter.get();
        }
    }
    private static final class UploadReadHandler implements ReadHandler{
        private final FileOutputStream out;
        private final NIOInputStream input;
        private final byte[] buf;
        private final AtomicLong uploadedBytesCounter;
        
        private UploadReadHandler(NIOInputStream inputStream, String filename, AtomicLong uploadedBytesCounter) throws FileNotFoundException{
            out = new FileOutputStream(filename);
            input = inputStream;
            buf = new byte[2048];
            this.uploadedBytesCounter = uploadedBytesCounter;
        }

        @Override
        public void onDataAvailable() throws Exception {
            readAndSave();
            input.notifyAvailable(this);
        }

        @Override
        public void onAllDataRead() throws Exception {
            readAndSave();
            finish();
        }

        @Override
        public void onError(Throwable thrwbl) {
            finish();
        }

        private void readAndSave() throws IOException{
            while (input.isReady()) {
                int readBytes = input.read(buf);
                out.write(buf, 0, readBytes);
                uploadedBytesCounter.addAndGet(readBytes);
            }
        }

        private void finish(){
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }
}
