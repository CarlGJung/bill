package top.carljung.bill.jersey;

import com.google.protobuf.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static top.carljung.bill.server.MediaType.APPLICATION_JSON;
import static top.carljung.bill.server.MediaType.APPLICATION_PROTOBUF;
/**
 *
 * @author wangchao
 */
public class ProtobufProvider{
    private static final Logger logger = LoggerFactory.getLogger(ProtobufProvider.class);
    
    @Provider
    @Consumes({APPLICATION_JSON, APPLICATION_PROTOBUF})
    public static class  ProtobufReader implements MessageBodyReader<Message>{
        @Override
        public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return Message.class.isAssignableFrom(type);
        }

        @Override
        public Message readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
            try {
                Method newBuilder = type.getMethod("newBuilder");
                Message.Builder builder = (Message.Builder)newBuilder.invoke(type);
                return builder.mergeFrom(entityStream).build();
            } catch (Exception ex) {
                throw new WebApplicationException(ex);
            }
        }
    }
    
    @Provider
    @Produces({APPLICATION_JSON, APPLICATION_PROTOBUF})
    public static class  ProtobufWriter implements MessageBodyWriter<Message>{

        @Override
        public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return Message.class.isAssignableFrom(type);
        }
        
        private Map<Message, byte[]> buffer = new WeakHashMap<>();
        
        @Override
        public long getSize(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            try {  
                m.writeTo(baos);  
            } catch (IOException e) {  
                return -1;  
            }  
            byte[] bytes = baos.toByteArray();  
            buffer.put(m, bytes);  
            return bytes.length; 
        }
        
        @Override
        public void writeTo(Message m, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
            byte[] b = buffer.remove(m);
            if (b == null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                try {  
                    m.writeTo(baos);
                    b = baos.toByteArray();
                } catch (IOException e) {
                    b = new byte[0];
                    logger.warn("write protobuf message fail", e);
                }
            }
            entityStream.write(b);
        }
    }
}
