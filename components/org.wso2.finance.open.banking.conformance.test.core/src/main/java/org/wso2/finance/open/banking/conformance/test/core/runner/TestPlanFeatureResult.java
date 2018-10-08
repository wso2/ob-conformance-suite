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

package org.wso2.finance.open.banking.conformance.test.core.runner;

import com.google.gson.JsonObject;
import org.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

/**
 * Class that holds results of a feature.
 */
public class TestPlanFeatureResult {

    private JsonObject featureResult;
    private AttributeGroup attributeGroup;
    private String specName;
    private Report.RunnerState runnerState;

    public void setFeatureResult(JsonObject featureResult) {

        this.featureResult = featureResult;
    }

    public void setAttributeGroup(AttributeGroup attributeGroup) {

        this.attributeGroup = attributeGroup;
    }

    public void setSpecName(String specName) {

        this.specName = specName;
    }

    public void setRunnerState(Report.RunnerState runnerState) {

        this.runnerState = runnerState;
    }

    public JsonObject getFeatureResult() {

        return featureResult;
    }

    public AttributeGroup getAttributeGroup() {

        return attributeGroup;
    }

    public String getSpecName() {

        return specName;
    }

    public Report.RunnerState getRunnerState() {

        return runnerState;
    }
}
