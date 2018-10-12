package org.wso2.finance.open.banking.conformance.mgt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class consists of static methods for initial database operations
 * (Retrieving a database connection, Creating tables)
 */
public class DBConnector {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    private static final String USER = "obuser";
    private static final String PASS = "obpass";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            // System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }

    public static void createTables(){
        Connection conn = DBConnector.getConnection();
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            System.out.println("Creating tables in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   TestPlan " +
                    "(testID VARCHAR(100) not NULL, " +
                    " userID VARCHAR(50), " +
                    " testConfig CLOB, " +
                    " creationTime DATETIME, " +
                    " PRIMARY KEY ( testID, userID ))";
            stmt.executeUpdate(sql);
            System.out.println("Created TestPlan table in given database...");

            sql =  "CREATE TABLE   Report " +
                    "(reportID INT not NULL, " +
                    "testID VARCHAR(100) not NULL, " +
                    " userID VARCHAR(50), " +
                    " report CLOB, " +
                    " runTime DATETIME, " +
                    " PRIMARY KEY ( reportID, testID, userID ))";
            stmt.executeUpdate(sql);
            System.out.println("Created Report table in given database...");

            // Clean-up
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
