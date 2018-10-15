package org.wso2.finance.open.banking.conformance.mgt.dao.impl;

import org.wso2.finance.open.banking.conformance.mgt.dao.UserDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.dto.UserDTO;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
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
            stmt.setString(3, userDTO.getPassword()); // TODO: encrypt password
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

    @Override
    public void updateUser(UserDTO userDTO) {
        Connection conn = DBConnector.getConnection();

        PreparedStatement stmt = null;
        String sql;
        try {
            sql = SQLConstants.UPDATE_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userDTO.getUserName());
            stmt.setString(2, userDTO.getPassword()); // TODO: encrypt password
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

    @Override
    public UserDTO getUser(String userID, String password) {
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;
        UserDTO userDTO = null;

        try {
            String sql = SQLConstants.GET_USER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setString(2, password); // TODO: Encrypt password
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
