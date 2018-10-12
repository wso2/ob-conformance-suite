package org.wso2.finance.open.banking.conformance.mgt.db;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;


/**
 * This class is responsible for handling all initial database
 * operations.
 */
public class DBConnector {
    private static BasicDataSource dataSource;
    private static final String DB_URL = "jdbc:h2:~/obsuit-reports";
    private static final String JDBC_DRIVER = "org.h2.Driver";

    //  Database credentials
    private static final String USER = "obuser";
    private static final String PASS = "obpass";

    /**
     *This method will return an SQL Connection for accessing
     *the database from the connection pool.
     * @return Connection - An SQL Connection
     */
    public static Connection getConnection(){
        Connection conn = null;
        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl(DB_URL);
            ds.setUsername(USER);
            ds.setPassword(PASS);
            ds.setDriverClassName(JDBC_DRIVER);

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            dataSource = ds;
        }
        try {
            conn = dataSource.getConnection();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }
}
