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

package org.wso2.finance.open.banking.conformance.mgt.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.TransactionException;
import org.wso2.finance.open.banking.conformance.mgt.dao.UserDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.dto.UserDTO;
import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;

import java.sql.Date;

public class UserDAOImpl implements UserDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(UserDTO userDTO) throws ConformanceMgtException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {
            jdbcTemplate.withTransaction(template ->
                    template.executeInsert(SQLConstants.ADD_USER, preparedStatement -> {
                        preparedStatement.setString(1, userDTO.getUserID());
                        preparedStatement.setString(2, userDTO.getUserName());
                        preparedStatement.setString(3, userDTO.getPassword());
                        preparedStatement.setString(3, currentTime);
                    }, null, false)
            );
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error adding user '"+userDTO.getUserID()+"' to the User table", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(UserDTO userDTO) throws ConformanceMgtException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());

        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.UPDATE_USER, preparedStatement -> {
                    preparedStatement.setString(1, userDTO.getUserName());
                    preparedStatement.setString(2, userDTO.getPassword());
                    preparedStatement.setString(3, userDTO.getUserID());
                });
                return null;
            });
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error updating user details of the user '"+userDTO.getUserID()+"'", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUser(String userID, String password) throws ConformanceMgtException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        UserDTO userDTO = null;

        try {
            userDTO = jdbcTemplate.withTransaction(template -> jdbcTemplate.fetchSingleRecord(SQLConstants.GET_USER,
                    (resultSet, rowNumber) -> {
                        String name = resultSet.getString("name");
                        Date regDate = resultSet.getDate("regDate");
                        return new UserDTO(userID, name, password, regDate);
                    }, preparedStatement -> {
                        preparedStatement.setString(1, userID);
                        preparedStatement.setString(2, UserDTO.encryptPassword(password));
                    }
            ));
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving user datails of the user '"+userID+"'", e);
        }
        return userDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(String userID) throws ConformanceMgtException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.REMOVE_USER, preparedStatement ->
                        preparedStatement.setString(1, userID));
                return null;
            });
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error deleting user '"+userID+"'", e);
        }
    }
}
