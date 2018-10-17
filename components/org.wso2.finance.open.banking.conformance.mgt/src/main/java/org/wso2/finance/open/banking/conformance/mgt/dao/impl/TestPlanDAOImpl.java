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
import org.wso2.finance.open.banking.conformance.mgt.dao.TestPlanDAO;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.db.SQLConstants;
import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.stream.Collectors;
import java.util.ArrayList;
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
    public int storeTestPlan(String userID, TestPlan testPlan) throws ConformanceMgtException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        Gson gson = new Gson();
        int generatedTestID = -1;

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {
            String testPlanJson = gson.toJson(testPlan);
            generatedTestID =  jdbcTemplate.withTransaction(template ->
                    template.executeInsert(SQLConstants.CREATE_TESTPLAN, preparedStatement -> {
                        preparedStatement.setString(1, userID);
                        preparedStatement.setString(2, testPlanJson);
                        preparedStatement.setString(3, currentTime);
                    }, null, true)
            );
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error inserting test plan '"+testPlan.getName()+"' to the TestPlan table", e);
        }
        testPlan.setTestId(generatedTestID);
        updateTestPlan(generatedTestID, testPlan);
        return generatedTestID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTestPlan(int testID, TestPlan testPlan) throws ConformanceMgtException{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        Gson gson = new Gson();

        try {
            jdbcTemplate.withTransaction(template -> {
                String testPlanJson = gson.toJson(testPlan);
                template.executeUpdate(SQLConstants.UPDATE_TESTPLAN, preparedStatement -> {
                    preparedStatement.setString(1, testPlanJson);
                    preparedStatement.setInt(2, testID);
                });
                return null;
            });
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error updating test plan '"+testPlan.getName()+"' with ID '"+testID+"' to the TestPlan table", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestPlan getTestPlan(int testID) throws ConformanceMgtException{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        TestPlan testPlan;
        Gson gson = new Gson();

        try {
            testPlan = jdbcTemplate.withTransaction(template -> jdbcTemplate.fetchSingleRecord(SQLConstants.RETRIEVE_TESTPLAN,
                    (resultSet, rowNumber) -> {
                        String testPlanJson = resultSet.getString("testConfig");
                        return gson.fromJson(testPlanJson, TestPlan.class);
                    }, preparedStatement -> preparedStatement.setInt(1, testID)));
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving test plan with ID '"+testID+"' from the TestPlan table", e);
        }
        return testPlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, TestPlanDTO> getTestPlans(String userID) throws ConformanceMgtException{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnector.getDataSource());
        ReportDAO reportDAO = new ReportDAOImpl();
        Map<Integer, List<Report>> testReportMap = reportDAO.getReportsForUser(userID);
        Gson gson = new Gson();

        try {
            List<TestPlanDTO> testPlanDTOList = jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.RETRIEVE_TESTPLANS, ((resultSet, rowNumber) -> {
                        String testPlanJson = resultSet.getString("testConfig");
                        TestPlan testPlan = gson.fromJson(testPlanJson, TestPlan.class);
                        return new TestPlanDTO(testPlan.getTestId(), testPlan, testReportMap.getOrDefault(testPlan.getTestId(), new ArrayList<>()));
                    }), preparedStatement -> {
                        preparedStatement.setString(1,userID);
                    })
            );
            return testPlanDTOList.stream().collect(Collectors.toMap(TestPlanDTO::getTestId, TestPlanDTO -> TestPlanDTO));
        } catch (TransactionException e) {
            throw new ConformanceMgtException("Error retrieving test plans for the user '"+userID, e);
        }
    }
}