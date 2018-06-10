package top.carljung.bill.server;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import top.carljung.bill.config.BillProto;
import top.carljung.bill.config.Configuration;

/**
 *
 * @author wangchao
 */
public class ServerMain {
    public static void main(String[] args){
        BillProto.Server serverConfig = Configuration.instance.getServerConfig();
        int port = serverConfig.getPort();
        final URI BASE_URI = URI.create("http://0.0.0.0:" + port + "/");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI,
                    new Application(), false);
        final ServerConfiguration config = server.getServerConfiguration();
        String docRoot = serverConfig.getDocRoot();
        StaticHttpHandler staticHandler = new StaticHttpHandler(docRoot + "/static");
        staticHandler.setFileCacheEnabled(false);
        config.addHttpHandler(staticHandler, "/static");
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        
        try {
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
