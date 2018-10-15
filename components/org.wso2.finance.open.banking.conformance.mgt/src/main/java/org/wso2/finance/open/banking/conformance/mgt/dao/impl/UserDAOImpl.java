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

import org.wso2.finance.open.banking.conformance.mgt.dao.UserDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.dto.UserDTO;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    /**
     *This method will add a new row to the user table when a new user is registered.
     * @param userDTO : UserDTO object
     */
    @Override
    public void addUser(UserDTO userDTO) {
        Connection conn = DBConnector.getConnection();

        java.util.Date dt = userDTO.getRegDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String regDate = sdf.format(dt);
        PreparedStatement stmt = null;
        String sql;
        try {
            sql = SQLConstants.ADD_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userDTO.getUserID());
            stmt.setString(2, userDTO.getUserName());
            stmt.setString(3, userDTO.getPassword());
            stmt.setString(3, regDate);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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

    /**
     *This method will update an existing row in the user table when a UserDTO is given.
     * @param userDTO : UserDTO object
     */
    @Override
    public void updateUser(UserDTO userDTO) {
        Connection conn = DBConnector.getConnection();

        PreparedStatement stmt = null;
        String sql;
        try {
            sql = SQLConstants.UPDATE_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userDTO.getUserName());
            stmt.setString(2, userDTO.getPassword());
            stmt.setString(3, userDTO.getUserID());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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

    /**
     *This method will return a UserDTO object when the username and password
     * for a particular user is given.
     * @param userID : ID of the user
     * @param password : Password of the user
     * @return UserDTO object for the requested user.
     */
    @Override
    public UserDTO getUser(String userID, String password) {
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;
        UserDTO userDTO = null;

        try {
            String sql = SQLConstants.GET_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setString(2, UserDTO.encryptPassword(password));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userDTO = new UserDTO(rs.getString("userID"),
                        rs.getString("name"),rs.getString("password"), rs.getDate("regDate"));
            }
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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
        return userDTO;
    }

    /**
     *This method will delete the row in the user table belonging to the
     * given userID.
     * @param userID : ID of the user
     */
    @Override
    public void deleteUser(String userID) {
        Connection conn = DBConnector.getConnection();

        PreparedStatement stmt = null;
        String sql;
        try {
            sql = SQLConstants.REMOVE_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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
