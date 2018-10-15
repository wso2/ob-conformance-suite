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

    /**
     *This method will add the report to the database.
     * @param userID : User ID of the current user
     * @param testID : Test ID of the report
     * @param report : Report object
     */
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
        return generatedReportID;
    }

    /**
     *This method will create a row in the report table to store the
     * report after the test has finished.
     * @param userID : User ID of the current user
     * @param testID : Test ID of the report
     * @return the generated report id (from auto increment column of the report table)
     */
    @Override
    public int getNewReportID(String userID, int testID) {
        Connection conn = DBConnector.getConnection();
        int generatedReportID = -1;
        PreparedStatement stmt = null;
        try {
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
        return generatedReportID;
    }

    /**
     *This method will add a report to the row created in the report table
     *at the start of a test.
     * @param reportID : ID of the report
     * @param report : Report object
     */
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
            String sql = SQLConstants.UPDATE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, reportJson);
            stmt.setString(2, currentTime);
            stmt.setInt(3, reportID);
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
     *This method will return a report object when the reportID is given.
     * @param userID : User ID of the current user
     * @param testID : Test ID of the report
     * @param reportID : ID of the report
     * @return the requested report
     */
    @Override
    public Report getReport(String userID, int testID, int reportID) {
        Gson gson = new Gson();
        Report report = null;
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = SQLConstants.RETRIEVE_REPORT;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setInt(2, testID);
            stmt.setInt(3, reportID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String reportJson = rs.getString("report");
                report = gson.fromJson(reportJson, Report.class);
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
        return report;


    }

    /**
     *This method will return all reports belonging to a particular test.
     * @param userID : User ID of the current user
     * @param testID : Test ID of the reports
     * @return a List with requested reports
     */
    @Override
    public List<Report> getReports(String userID, int testID) {

        Gson gson = new Gson();
        List<Report> reports = new ArrayList<Report>();
        Connection conn = DBConnector.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = SQLConstants.DELETE_EMPTY_REPORTS;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            sql = SQLConstants.RETRIEVE_REPORTS;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setInt(2, testID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int reportID = rs.getInt("reportID");
                String reportJson = rs.getString("report");
                Report report = gson.fromJson(reportJson, Report.class);
                reports.add(report);
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
        return reports;
    }
}