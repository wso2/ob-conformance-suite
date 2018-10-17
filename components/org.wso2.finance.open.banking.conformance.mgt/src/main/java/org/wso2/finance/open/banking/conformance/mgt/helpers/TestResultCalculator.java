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

package org.wso2.finance.open.banking.conformance.mgt.helpers;

import org.apache.log4j.Logger;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.models.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Helper class for calculating test results summary.
 */
public class TestResultCalculator {

    private static Logger log = Logger.getLogger(TestResultCalculator.class);

    private TestResultCalculator(){

    }

    public static Map<String, Integer> getSummaryResults(Report report) {
        Map<String, Integer> summaryResults = new HashMap<String, Integer>();
        Integer passed = 0;
        Integer failed = 0;
        Integer total = 0;
        Map<String, List<Result>> results = report.getResult();
        for (Map.Entry<String, List<Result>> entry :results.entrySet()) {
            List<Result> result = entry.getValue();
            for (Result feature : result) {
                String status = feature.getFeatureStatus();
                if (status.equals("passed")) {
                    passed = passed +1;
                } else {
                    failed = failed + 1;
                }
                total = total + 1;
            }

        }
        summaryResults.put("passed", passed);
        summaryResults.put("failed", failed);
        summaryResults.put("total", total);

        log.debug("passed : " + passed + " failed : "+ failed + " total : " + total);
        return summaryResults;
    }
}
