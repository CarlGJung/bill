package top.carljung.bill.server;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import top.carljung.bill.handler.ResourceHandler;

/**
 *
 * @author wangchao
 */
public class ServerMain {
    public static void main(String[] args){
        final URI BASE_URI = URI.create("http://0.0.0.0:18080/");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI,
                    new Application(), false);
        final ServerConfiguration config = server.getServerConfiguration();
        config.addHttpHandler(ResourceHandler.instance, "/");
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        
        try {
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
