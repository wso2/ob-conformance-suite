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

package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;

/**
 * Interface to create a ReportDAO.
 */
public interface ReportDAO {

    /**
     *This method will create a row in the report table to store the
     * report after the test has finished.
     * @param userID : User ID of the current user
     * @param testID : Test ID of the report
     * @return the generated report id (from auto increment column of the report table)
     */
    public int getNewReportID(String userID, int testID);

    /**
     *This method will add a report to the row created in the report table
     *at the start of a test.
     * @param reportID : ID of the report
     * @param report : Report object
     */
    public void updateReport(int reportID, Report report);

    /**
     *This method will remove any rows in the report table
     * with  a null report field.
     */
    public void deleteEmptyReports();

    /**
     *This method will return a report object when the reportID is given.
     * @param reportID : ID of the report
     * @return the requested report
     */
    public Report getReport(int reportID);

    /**
     *This method will return all reports belonging to a particular test.
     * @param testID : Test ID of the reports
     * @return a List with requested reports
     */
    public List<Report> getReports(int testID);
}
