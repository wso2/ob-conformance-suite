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
        try {
            // Execute query
            String sql = SQLConstants.CREATE_TESTPLAN;
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userID);
            stmt.setString(2, testPlanJson);
            stmt.setString(3, currentTime);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                generatedTestID = rs.getInt(1);
            }
            // System.out.println("Data Added to DB........");

            // Clean-up
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        // System.out.println("Exit from storeTestPlan");
        return generatedTestID;
    }

    @Override
    public TestPlan getTestPlan(String userID, String testID) {
        Gson gson = new Gson();
        TestPlan testPlan = new TestPlan();
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            // Execute query
            String sql =  SQLConstants.RETRIEVE_TESTPLAN;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setString(2, testID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String testPlanJson = rs.getString("testConfig");
                String creationTime = rs.getString("creationTime");
                testPlan = gson.fromJson(testPlanJson, TestPlan.class);
            }
            // Clean-up
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        // System.out.println("Exit from getTestPlan");

        return testPlan;
    }

    @Override
    public Map<String, TestPlanDTO> getTestPlans(String userID) {
        Gson gson = new Gson();
        Map<String, TestPlanDTO> testPlans = new HashMap<String, TestPlanDTO>();
        Connection conn = DBConnector.getConnection();
        ReportDAO reportDAO = new ReportDAOImpl();
        List<Report> reports = null;
        PreparedStatement stmt = null;

        try {
            // Execute query
            String sql =  SQLConstants.RETRIEVE_TESTPLANS;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String testID = rs.getString("testID");
                String testPlanJson = rs.getString("testConfig");
                String creationTime = rs.getString("creationTime");

                TestPlan testPlan = gson.fromJson(testPlanJson, TestPlan.class);
                reports = reportDAO.getReports("adminx", testID);
                // System.out.println(reports.toString());
                TestPlanDTO testPlanDTO = new TestPlanDTO(testID, testPlan, reports);
                testPlans.put(testID, testPlanDTO);
                // System.out.println(testPlans.toString());
            }
            // Clean-up
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        // System.out.println("Exit from getTestPlans");

        return testPlans;
    }
}