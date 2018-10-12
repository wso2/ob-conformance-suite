package org.wso2.finance.open.banking.conformance.mgt.dao.impl;

import com.google.gson.Gson;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ReportDAO
 * Responsible for the database operations regarding reports
 */
public class ReportDAOImpl implements ReportDAO {

    @Override
    public void storeReport(String userID, String uuid, Report report) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        String reportJson = gson.toJson(report);
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql =  "INSERT INTO Report VALUES  (?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setString(2, uuid);
            stmt.setString(3, userID);
            stmt.setString(4, reportJson);
            stmt.setString(5, currentTime);
            stmt.executeUpdate();
            // System.out.println("Report data Added to DB........");

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
        // System.out.println("Exit from REPORT-DAO!");

    }

    @Override
    public Report getReport(String userID, String uuid, int reportID) {
        Gson gson = new Gson();
        Report report = null;
        Connection conn = DBConnector.getConnection();
        Statement stmt = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            String sql =  "SELECT * FROM Report WHERE userID='"+userID+"' AND testID='"+uuid+"' AND reportID='"+reportID+"'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String reportJson = rs.getString("report");
                String creationTime = rs.getString("runTime");
                report = gson.fromJson(reportJson, Report.class);

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
        // System.out.println("Exit from storeReport()");

        return report;


    }

    @Override
    public List<Report> getReports(String userID, String uuid) {

        Gson gson = new Gson();
        List<Report> reports = new ArrayList<Report>();
        Connection conn = DBConnector.getConnection();
        Statement stmt = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            String sql =  "SELECT * FROM Report WHERE testID='"+uuid+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int reportID = rs.getInt("reportID");
                String reportJson = rs.getString("report");
                String runTime = rs.getString("runTime");

                Report report = gson.fromJson(reportJson, Report.class);
                reports.add(report);
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

        return reports;
    }
}