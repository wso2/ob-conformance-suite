package org.wso2.finance.open.banking.conformance.mgt.dao.impl;

import com.google.gson.Gson;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
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
    public int storeReport(String userID, int testID, Report report) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();
        int generatedReportID = -1;

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        String reportJson = gson.toJson(report);
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql = SQLConstants.CREATE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testID);
            stmt.setString(2, userID);
            stmt.setString(3, reportJson);
            stmt.setString(4, currentTime);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                generatedReportID = rs.getInt(1);
                report.setReportId(generatedReportID);
                reportJson = gson.toJson(report);
                sql = SQLConstants.UPDATE_REPORT;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, reportJson);
                stmt.setString(2, currentTime);
                stmt.setInt(3, generatedReportID);
                stmt.executeUpdate();
            }
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
        return generatedReportID;
    }

    @Override
    public int getNewReportID(String userID, int testID) {
        Connection conn = DBConnector.getConnection();
        int generatedReportID = -1;
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql = SQLConstants.CREATE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testID);
            stmt.setString(2, userID);
            stmt.setNull(3, Types.NULL);
            stmt.setNull(4, Types.NULL);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                generatedReportID = rs.getInt(1);
            }
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
        return generatedReportID;
    }

    @Override
    public void updateReport(int reportID, Report report) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        String reportJson = gson.toJson(report);
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql = SQLConstants.UPDATE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, reportJson);
            stmt.setString(2, currentTime);
            stmt.setInt(3, reportID);
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
    public Report getReport(String userID, int testID, int reportID) {
        Gson gson = new Gson();
        Report report = null;
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            // Execute query
            String sql = SQLConstants.RETRIEVE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setInt(2, testID);
            stmt.setInt(3, reportID);
            ResultSet rs = stmt.executeQuery();

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
    public List<Report> getReports(String userID, int testID) {

        Gson gson = new Gson();
        List<Report> reports = new ArrayList<Report>();
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            // Execute query
            String sql = SQLConstants.RETRIEVE_REPORTS;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setInt(2, testID);
            ResultSet rs = stmt.executeQuery();

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