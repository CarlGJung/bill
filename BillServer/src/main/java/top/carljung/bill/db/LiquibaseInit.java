package top.carljung.bill.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.carljung.bill.config.Configuration;
import top.carljung.bill.proto.ConfigStore;

/**
 *
 * @author wangchao
 */
public class LiquibaseInit {
    private static final Logger logger = LoggerFactory.getLogger(LiquibaseInit.class);
    
    public void init() throws LiquibaseException, SQLException{
        init(Configuration.instance.getDBConfig());
    }
    
    public void init(ConfigStore.DB dbConfig) throws LiquibaseException, SQLException{
        String dbFile = "db.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        String url = dbConfig.getUrl();
        Properties properties = new Properties();
        properties.setProperty("driver", dbConfig.getDriver());
        Connection connection = DriverManager.getConnection(url, properties);
        Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(dbFile,resourceAccessor, db);
        liquibase.update("");
    }
}
