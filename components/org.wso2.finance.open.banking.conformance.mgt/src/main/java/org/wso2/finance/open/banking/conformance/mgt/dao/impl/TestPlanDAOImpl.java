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
     *This method will store the given test-plan in the testPlan table.
     * @param userID : User ID of the current user
     * @param testPlan : TestPlan object
     * @return the generated test-plan id (from auto increment column of the testPlan table)
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
        String sql;
        try {
            sql = SQLConstants.CREATE_TESTPLAN;
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userID);
            stmt.setString(2, testPlanJson);
            stmt.setString(3, currentTime);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                generatedTestID = rs.getInt(1);
                testPlan.setTestId(generatedTestID);
                testPlanJson = gson.toJson(testPlan);
                sql = SQLConstants.UPDATE_TESTPLAN;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, testPlanJson);
                stmt.setInt(2, generatedTestID);
                stmt.executeUpdate();
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
        return generatedTestID;
    }

    /**
     *This method will return the test plan object when the testID is given.
     * @param testID : testID of the requested test plan.
     * @return the requested test plan object
     */
    @Override
    public TestPlan getTestPlan(String userID, int testID) {
        Gson gson = new Gson();
        TestPlan testPlan = new TestPlan();
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql =  SQLConstants.RETRIEVE_TESTPLAN;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setInt(2, testID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String testPlanJson = rs.getString("testConfig");
                testPlan = gson.fromJson(testPlanJson, TestPlan.class);
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
        return testPlan;
    }

    /**
     *This method will return all the test plans along with their corresponding
     *reports belonging to a particular user.
     * @param userID : User ID of the current user.
     * @return a map containing testPlan IDs and the corresponding testPlanDTOs.
     */
    @Override
    public Map<Integer, TestPlanDTO> getTestPlans(String userID) {
        Gson gson = new Gson();
        Map<Integer, TestPlanDTO> testPlans = new HashMap<Integer, TestPlanDTO>();
        Connection conn = DBConnector.getConnection();
        ReportDAO reportDAO = new ReportDAOImpl();
        List<Report> reports;
        PreparedStatement stmt = null;

        try {
            // Execute query
            String sql =  SQLConstants.RETRIEVE_TESTPLANS;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int testID = rs.getInt("testID");
                String testPlanJson = rs.getString("testConfig");
                TestPlan testPlan = gson.fromJson(testPlanJson, TestPlan.class);
                reports = reportDAO.getReports(userID, testID);
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
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
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