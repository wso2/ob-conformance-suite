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

package org.wso2.finance.open.banking.conformance.mgt.models;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Model class representing the Report of a TestPlan.
 */
public class Report {

    private String testId;
    private Integer reportId;
    private Map<String, List<JsonObject>> result;
    private RunnerState state;
    private Date executed;

    /**
     * Enum representing the state of the Report.
     */
    public enum RunnerState {
        RUNNING, DONE, NOT_STARTED, WAITING
    }

    public Report(String testId, Integer reportId, Map<String, List<JsonObject>> result, RunnerState state,
                  Date executed) {

        this.testId = testId;
        this.reportId = reportId;
        this.result = result;
        this.state = state;
        this.executed = new Date(executed.getTime());
    }

    public String getTestId() {

        return testId;
    }

    public void setTestId(String testId) {

        this.testId = testId;
    }

    public Integer getReportId() {

        return reportId;
    }

    public void setReportId(Integer reportId) {

        this.reportId = reportId;
    }

    public Map<String, List<JsonObject>> getResult() {

        return result;
    }

    public void setResult(Map<String, List<JsonObject>> result) {

        this.result = result;
    }

    public RunnerState getState() {

        return state;
    }

    public void setState(RunnerState state) {

        this.state = state;
    }

    public Date getExecuted() {

        return new Date(executed.getTime());
    }

    public void setExecuted(Date executed) {

        this.executed = new Date(executed.getTime());
    }
}
