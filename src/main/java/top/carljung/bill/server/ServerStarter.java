package top.carljung.bill.server;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import liquibase.exception.LiquibaseException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.LoggerFactory;
import top.carljung.bill.config.BillProto;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.db.LiquibaseInit;

/**
 *
 * @author wangchao
 */
public class ServerStarter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServerStarter.class);
    
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
            ServerStarter starter = new ServerStarter();
            starter.init();
            server.start();
        } catch (IOException | LiquibaseException | SQLException ex) {
            logger.warn("server fail", ex);
            System.exit(1);
        }
    }
    
    public void init() throws IOException, LiquibaseException, SQLException{
        Configuration.instance.readConfig();
        new LiquibaseInit().init();
    }
}
