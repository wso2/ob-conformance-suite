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
