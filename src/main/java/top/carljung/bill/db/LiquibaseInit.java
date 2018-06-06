package top.carljung.bill.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

/**
 *
 * @author wangchao
 */
public class LiquibaseInit {
    public void init(){
        String dbFile = "db.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        String url = "jdbc:mysql://localhost:3306/bill?user=root&password=123456&useUnicode=true&characterEncoding=utf-8&createDatabaseIfNotExist=true&autoReconnect=true&failOverReadOnly=false";
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(dbFile,resourceAccessor, db);
            liquibase.update("");
        } catch (SQLException ex) {
            Logger.getLogger(LiquibaseInit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LiquibaseException ex) {
            Logger.getLogger(LiquibaseInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
