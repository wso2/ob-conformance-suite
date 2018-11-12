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
import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for handling all initial database
 * operations.
 */
public class DBConnector {
    private static BasicDataSource dataSource;
    private static Logger log = Logger.getLogger(DBConnector.class);


    private DBConnector(){}
    /**
     *This method will return an SQL Connection for accessing
     *the database from the connection pool.
     * @return Connection - An SQL Connection
     */
    public static synchronized DataSource getDataSource() {
            if (dataSource == null) {
                try(FileInputStream fileInputStream = new FileInputStream("db.properties")){
                    Properties props = new Properties ();
                    props.load (fileInputStream);

                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName((String) props.get ("driverClassName"));
                    ds.setUrl((String) props.get ("URL"));
                    ds.setUsername((String) props.get ("userName"));
                    ds.setPassword((String) props.get ("password"));

                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
            }catch (IOException e){
                    log.error("Error reading database properties from 'db.properties' file",e);
                }
        }

        return dataSource;
    }

    /**
     *This method will execute queries that create
     * the tables needed for the application if they are
     * not available.
     */
    public static void createTablesIfNotExist(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.CREATE_USER_TABLE);
                template.executeUpdate(SQLConstants.CREATE_TESTPLAN_TABLE);
                template.executeUpdate(SQLConstants.CREATE_REPORT_TABLE);
                template.executeUpdate(
                        "MERGE INTO User KEY (userID) VALUES (" +
                                "'adminx', 'Administrator', 'sha512', " +
                                "'2018-10-17 14:50:26')"
                ); //TODO : Remove after implementing user registration functionality

                return null;
                });


        } catch (Exception e) {
            log.error("Error creating tables",e);
        }
    }
}
