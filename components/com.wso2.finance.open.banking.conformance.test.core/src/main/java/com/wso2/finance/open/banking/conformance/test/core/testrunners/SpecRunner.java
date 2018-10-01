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

package com.wso2.finance.open.banking.conformance.test.core.testrunners;

import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Execute Features from a Specification.
 */
public class SpecRunner {

    private static Logger log = Logger.getLogger(SpecRunner.class);

    private Specification specification;

    /**
     * @param specification
     */
    public SpecRunner(Specification specification) {

        this.specification = specification;
    }

    /**
     * Run each feature in the spec.
     *
     * @return
     */
    public List<JsonObject> runSpecification() {

        log.debug("Start Running TestSpec " + specification.getName() + " version:" + specification.getVersion());

        List<JsonObject> results = new ArrayList();

        Context.getInstance().setSpecContext(specification.getName(), specification.getVersion());

        for (Feature feature : specification.getFeatures()) {
            FeatureRunner featureRunner = new FeatureRunner(feature);
            results.add(featureRunner.runFeature());
        }

        Context.getInstance().clearSpecContext();
        log.debug("End Running TestSpec " + specification.getName() + " version:" + specification.getVersion());

        return results;

    }
}
