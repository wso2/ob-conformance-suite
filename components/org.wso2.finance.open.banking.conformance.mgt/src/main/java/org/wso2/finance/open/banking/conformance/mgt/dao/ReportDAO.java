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

import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;
import java.util.Map;

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
     * @throws ConformanceMgtException
     */
    public int getNewReportID(String userID, int testID) throws ConformanceMgtException;

    /**
     *This method will add a report to the row created in the report table
     *at the start of a test.
     * @param reportID : ID of the report
     * @param report : Report object
     * @throws ConformanceMgtException
     */
    public void updateReport(int reportID, Report report) throws ConformanceMgtException;

    /**
     *This method will remove any rows in the report table
     * with  a null report field.
     */
    public void deleteEmptyReports() throws ConformanceMgtException;

    /**
     *This method will return a report object when the reportID is given.
     * @param reportID : ID of the report
     * @return the requested report
     * @throws ConformanceMgtException
     */
    public Report getReport(int reportID) throws ConformanceMgtException;

    /**
     *This method will return all reports belonging to a particular test.
     * @param testID : Test ID of the reports
     * @return a List with requested reports
     * @throws ConformanceMgtException
     */
    public List<Report> getReportsForTest(int testID) throws ConformanceMgtException;

    /**
     *This method will return all reports for a particular user, mapping
     * them with testID.
     * @param userID : ID of the user
     * @return a Map with TestID and corresponding list of reports.
     * @throws ConformanceMgtException
     */
    public Map<Integer, List<Report>> getReportsForUser(String userID) throws ConformanceMgtException;
}
