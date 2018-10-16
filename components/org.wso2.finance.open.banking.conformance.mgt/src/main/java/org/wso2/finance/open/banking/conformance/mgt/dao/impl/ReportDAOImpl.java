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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNewReportID(String userID, int testID) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        int generatedReportID = -1;

        try {
            generatedReportID =  jdbcTemplate.withTransaction(template ->
                    template.executeInsert(SQLConstants.CREATE_REPORT, preparedStatement -> {
                        preparedStatement.setInt(1, testID);
                        preparedStatement.setString(2, userID);
                        preparedStatement.setNull(3, Types.NULL);
                        preparedStatement.setNull(4, Types.NULL);
                    }, null, true)
            );
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        return generatedReportID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReport(int reportID, Report report) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        Gson gson = new Gson();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {
            String reportJson = gson.toJson(report);
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.UPDATE_REPORT, preparedStatement -> {
                    preparedStatement.setString(1, reportJson);
                    preparedStatement.setString(2, currentTime);
                    preparedStatement.setInt(3, reportID);
                });
                return null;
            });
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEmptyReports() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.DELETE_EMPTY_REPORTS);
                return null;
            });
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report getReport(int reportID) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        Report report = null;
        Gson gson = new Gson();

        try {
            report = jdbcTemplate.withTransaction(template -> jdbcTemplate.fetchSingleRecord(SQLConstants.RETRIEVE_REPORT,
                    (resultSet, rowNumber) -> {
                        String reportJson = resultSet.getString("report");
                        return gson.fromJson(reportJson, Report.class);
                    }, preparedStatement -> preparedStatement.setInt(1, reportID)));
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Report> getReportsForTest(int testID) {
        deleteEmptyReports();
        Gson gson = new Gson();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        List<Report> reports = null;

        try {
            reports = jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.RETRIEVE_REPORTS_FOR_TESTPLAN, ((resultSet, rowNumber) -> {
                        String reportJson = resultSet.getString("report");
                        return gson.fromJson(reportJson, Report.class); // return report
                    }), preparedStatement -> preparedStatement.setInt(1, testID))
            );
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        return reports;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, List<Report>> getReportsForUser(String userID) {
        deleteEmptyReports();
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
            e.printStackTrace();
        }
        return reportMap;
    }
}