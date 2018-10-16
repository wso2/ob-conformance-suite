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

import com.google.gson.Gson;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.dao.TestPlanDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the TestPlanDAO
 * Responsible for the database operations regarding test-plans
 */
public class TestPlanDAOImpl implements TestPlanDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public int storeTestPlan(String userID, TestPlan testPlan) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();
        int generatedTestID = -1;

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String testPlanJson = gson.toJson(testPlan);
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        String sql;
        try {
            sql = SQLConstants.CREATE_TESTPLAN;
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userID);
            stmt.setString(2, testPlanJson);
            stmt.setString(3, currentTime);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                generatedTestID = rs.getInt(1);
                testPlan.setTestId(generatedTestID);
                testPlanJson = gson.toJson(testPlan);
                sql = SQLConstants.UPDATE_TESTPLAN;
                stmt2 = conn.prepareStatement(sql);
                stmt2.setString(1, testPlanJson);
                stmt2.setInt(2, generatedTestID);
                stmt2.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs!=null) rs.close();;
            } catch(SQLException se4) {
                se4.printStackTrace();
            }
            try{
                if(stmt2!=null) stmt2.close();
            } catch(SQLException se3) {
                se3.printStackTrace();
            }
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        return generatedTestID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestPlan getTestPlan(int testID) {
        Gson gson = new Gson();
        TestPlan testPlan = new TestPlan();
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql =  SQLConstants.RETRIEVE_TESTPLAN;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String testPlanJson = rs.getString("testConfig");
                testPlan = gson.fromJson(testPlanJson, TestPlan.class);
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs!=null) rs.close();;
            } catch(SQLException se3) {
                se3.printStackTrace();
            }
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        return testPlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, TestPlanDTO> getTestPlans(String userID) {
        Gson gson = new Gson();
        Map<Integer, TestPlanDTO> testPlans = new HashMap<Integer, TestPlanDTO>();
        Connection conn = DBConnector.getConnection();
        ReportDAO reportDAO = new ReportDAOImpl();
        List<Report> reports;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Execute query
            String sql =  SQLConstants.RETRIEVE_TESTPLANS;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int testID = rs.getInt("testID");
                String testPlanJson = rs.getString("testConfig");
                TestPlan testPlan = gson.fromJson(testPlanJson, TestPlan.class);
                reports = reportDAO.getReports(testID);
                TestPlanDTO testPlanDTO = new TestPlanDTO(testID, testPlan, reports);
                testPlans.put(testID, testPlanDTO);
            }
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs!=null) rs.close();;
            } catch(SQLException se3) {
                se3.printStackTrace();
            }
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        return testPlans;
    }
}