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


export default class TestReportHelper {

    getTestSummary(testData){
        var results = {passed : 0, failed : 0};
        var curResult;
        for (var spec in testData) {
            curResult = this.getSpecResult(testData[spec], this);
            results.passed += curResult.passed;
            results.failed += curResult.failed;
        }
        if(results.passed + results.failed === 0){
            results.rate = 0;
        }else{
            results.rate = (100*(results.passed/(results.passed+results.failed))).toFixed(2);
        }
        return results;
    }

    getSpecResult(spec, thisClass){
        var results = {passed : 0, failed : 0, rate: 0};
        var curResult;
        spec.forEach(function(feature){
            curResult = thisClass.getFeatureResult(feature, thisClass);
            results.passed += curResult.passed;
            results.failed += curResult.failed;
        });
        return results;
    }

    getFeatureResult(feature, thisClass){
        var results = {passed : 0, failed : 0};
        feature['elements'].forEach(function(scenario){
            thisClass.getScenarioResult(scenario,thisClass)===true ? results.passed += 1 : results.failed += 1;
        });
        return results;
    }

    getScenarioResult(scenario, thisClass){
         var status = true;
         scenario['steps'].forEach(function(step){
            status=status && (thisClass.getStepResult(step) === "passed");
         });
         return status;
    }

    getStepResult(step){
         return step['result']['status'];
    }

    getFeatureResultFraction(feature, thisClass){
        var result = thisClass.getFeatureResult(feature, thisClass);
        return result.passed+"/"+(result.passed+result.failed);
    }
}
