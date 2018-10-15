/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * This class will hold all SQL queries needed for the
 * application as constants.
 */
package org.wso2.finance.open.banking.conformance.mgt.db;

public class SQLConstants {
    /* Create Tables */
    public static final String CREATE_TESTPLAN_TABLE = "CREATE TABLE TestPlan " +
            "(testID INT not NULL AUTO_INCREMENT, " +
            " userID VARCHAR(50), " +
            " testConfig CLOB, " +
            " creationTime DATETIME, " +
            " PRIMARY KEY ( testID ))";

    public static final String CREATE_REPORT_TABLE = "CREATE TABLE Report " +
            "(reportID INT not NULL AUTO_INCREMENT, " +
            " testID VARCHAR(100) not NULL, " +
            " userID VARCHAR(50), " +
            " report CLOB, " +
            " passed INT, " +
            " failed INT, " +
            " passRate INT, " +
            " runTime DATETIME, " +
            " PRIMARY KEY ( reportID ))";

    public static final String CREATE_USER_TABLE = "CREATE TABLE User " +
            "(userID VARCHAR(50) not NULL, " +
            " name VARCHAR(100) not NULL, " +
            " password VARCHAR(512) not NULL, " +
            " regDate DATETIME not NULL, " +
            " PRIMARY KEY ( userID ))";


    /* Test Plan SQL */
    // Create a Test Plan
    public static final String CREATE_TESTPLAN =  "INSERT INTO TestPlan (userID, testConfig, creationTime) VALUES  (?,?,?)";

    // Update Test Paln
    public static final String UPDATE_TESTPLAN = "UPDATE TestPlan SET testConfig = ? WHERE testID= ?";

    // Retrieve a Test Plan
    public static final String RETRIEVE_TESTPLAN = "SELECT * FROM TestPlan WHERE userID= ? AND testID= ?";

    // Retrieve all Test Plans for a given user
    public static final String RETRIEVE_TESTPLANS = "SELECT * FROM TestPlan WHERE userID= ?";

    // Delete a TestPlan
    public static final String DELETE_TESTPLAN =  "DELETE FROM TestPlan WHERE testID = ?";


    /* Report SQL */
    // Create a Report
    public static final String CREATE_REPORT =  "INSERT INTO Report (testID, userID, report, runtime) VALUES  (?,?,?,?)";

    // Update Report
    public static final String UPDATE_REPORT = "UPDATE Report SET report = ?, runtime = ? WHERE reportID = ?";

    // Retrieve a Report
    public static final String RETRIEVE_REPORT = "SELECT * FROM Report WHERE userID = ? AND testID= ? AND reportID = ?";

    // Retrieve all Reports for a given testPlan
    public static final String RETRIEVE_REPORTS = "SELECT * FROM Report WHERE userID = ? AND testID = ?";

    // Delete a Report
    public static final String DELETE_REPORT =  "DELETE FROM Report WHERE reportID = ?";

    // Delete all reports for a test plan
    public static final String DELETE_REPORTS =  "DELETE FROM Report WHERE testID = ?";


    /* User SQL */
    // Add new user
    public static final String ADD_USER =  "INSERT INTO User (userID, name, password, regDate) VALUES  (?,?,?,?))";
    //INSERT INTO User (userID, name, password) VALUES  ('admin','imesh', HASH('SHA256',STRINGTOUTF8('adminpass'),1))

    // Update user details
    public static final String UPDATE_USER =  "UPDATE User SET name = ?, password = ? WHERE userID = ?";

    // Get user details
    public static final String GET_USER =  "SELECT * FROM user WHERE userID = ? AND password = ?";

    // Remove user
    public static final String REMOVE_USER =  "DELETE FROM User WHERE userID = ?";
}
