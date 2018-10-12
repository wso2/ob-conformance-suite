package org.wso2.finance.open.banking.conformance.mgt.db;

public class SQLConstants {
    // Create Tables
    public static final String CREATE_TESTPLAN_TABLE = "CREATE TABLE   TestPlan " +
            "(testID INT not NULL AUTO_INCREMENT, " +
            " userID VARCHAR(50), " +
            " testConfig CLOB, " +
            " creationTime DATETIME, " +
            " PRIMARY KEY ( testID ))";

    public static final String CREATE_REPORT_TABLE = "CREATE TABLE   Report " +
            "(reportID INT not NULL AUTO_INCREMENT, " +
            " testID VARCHAR(100) not NULL, " +
            " userID VARCHAR(50), " +
            " report CLOB, " +
            " runTime DATETIME, " +
            " PRIMARY KEY ( reportID ))";

    /* Test Plan SQL */
    // Create a Test Plan
    public static final String CREATE_TESTPLAN =  "INSERT INTO TestPlan (userID, testConfig, creationTime) VALUES  (?,?,?)";

    // Retrieve a Test Plan
    public static final String RETRIEVE_TESTPLAN = "SELECT * FROM TestPlan WHERE userID= ? AND testID= ?";

    // Retrieve all Test Plans for a given user
    public static final String RETRIEVE_TESTPLANS = "SELECT * FROM TestPlan WHERE userID= ?";

    /* Report SQL */
    // Create a Report
    public static final String CREATE_REPORT =  "INSERT INTO Report (testID, userID, report, runtime) VALUES  (?,?,?,?)";

    // Retrieve a Report
    public static final String RETRIEVE_REPORT = "SELECT * FROM Report WHERE userID= ? AND testID= ? AND reportID= ?";

    // Retrieve all Reports for a given testPlan
    public static final String RETRIEVE_REPORTS = "SELECT * FROM Report WHERE userID= ? AND testID= ?";
}
