package top.carljung.bill.jersey;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import static top.carljung.bill.server.MediaType.APPLICATION_JSON;
import static top.carljung.bill.server.MediaType.APPLICATION_PROTOBUF;
/**
 *
 * @author wangchao
 */
public class ProtobufProvider{
    
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
            entityStream.write(buffer.remove(m));
        }
    }
}
