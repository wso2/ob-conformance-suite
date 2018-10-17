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
import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.TransactionException;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;
import org.wso2.finance.open.banking.conformance.mgt.helpers.TestResultCalculator;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the ReportDAO
 * Responsible for the database operations regarding reports
 */
public class ReportDAOImpl implements ReportDAO {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNewReportID(String userID, int testID) throws ConformanceMgtException {
        int generatedReportID = -1;

        try {
            generatedReportID =  jdbcTemplate.withTransaction(template ->
                    template.executeInsert(SQLConstants.CREATE_REPORT, preparedStatement -> {
                        preparedStatement.setInt(1, testID);
                        preparedStatement.setString(2, userID);
                    }, null, true)
            );
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error getting auto increment report ID from Report table for the test ID '"+testID+"'", e);
        }
        return generatedReportID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReport(int reportID, Report report) throws ConformanceMgtException {
        Gson gson = new Gson();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {
            String reportJson = gson.toJson(report);
            Map<String, Integer> resultSummary = TestResultCalculator.getSummaryResults(report);
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.UPDATE_REPORT, preparedStatement -> {
                    preparedStatement.setString(1, reportJson);
                    preparedStatement.setInt(2, resultSummary.get("passed"));
                    preparedStatement.setInt(3, resultSummary.get("failed"));
                    preparedStatement.setString(4, currentTime);
                    preparedStatement.setInt(5, reportID);
                });
                return null;
            });
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error updating report with report ID '"+reportID+"'", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEmptyReports() throws ConformanceMgtException {
        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.DELETE_EMPTY_REPORTS);
                return null;
            });
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error deleting records with a NULL report field from the report table", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report getReport(int reportID) throws ConformanceMgtException {
        Report report = null;
        Gson gson = new Gson();

        try {
            report = jdbcTemplate.withTransaction(template -> jdbcTemplate.fetchSingleRecord(SQLConstants.RETRIEVE_REPORT,
                    (resultSet, rowNumber) -> {
                        String reportJson = resultSet.getString("report");
                        return gson.fromJson(reportJson, Report.class);
                    }, preparedStatement -> preparedStatement.setInt(1, reportID)));
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving report with report ID '"+reportID+"'", e);
        }
        return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Report> getReportsForTest(int testID) throws ConformanceMgtException {
        try{
            deleteEmptyReports();
        } catch (ConformanceMgtException e){
            throw e;
        }
        Gson gson = new Gson();
        List<Report> reports = null;

        try {
            reports = jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.RETRIEVE_REPORTS_FOR_TESTPLAN, ((resultSet, rowNumber) -> {
                        String reportJson = resultSet.getString("report");
                        return gson.fromJson(reportJson, Report.class); // return report
                    }), preparedStatement -> preparedStatement.setInt(1, testID))
            );
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving reports for the test ID '"+testID+"'", e);
        }
        return reports;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, List<Report>> getReportsForUser(String userID) throws ConformanceMgtException {
        try{
            deleteEmptyReports();
        } catch (ConformanceMgtException e){
            throw e;
        }
        Gson gson = new Gson();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        Map<Integer, List<Report>> reportMap = new HashMap<>();
        List<Report> reports;

        try {
            reports = jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.RETRIEVE_REPORTS_FOR_USER, ((resultSet, rowNumber) -> {
                        String reportJson = resultSet.getString("report");
                        return gson.fromJson(reportJson, Report.class); // return report
                    }), preparedStatement -> {
                        preparedStatement.setString(1, userID);
                    })
            );

            /* Split the report list to multiple lists according to testID and insert to a map
               Map<testID, List of reports with testID> */
            for (Report report : reports) {
                List<Report> reportList = reportMap.get(report.getTestId());
                if (reportList == null) {
                    reportList = new ArrayList<>();
                    reportMap.put(report.getTestId(), reportList);
                }
                reportList.add(report);
            }
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving reports for the user with ID '"+userID+"'", e);
        }
        return reportMap;
    }
}