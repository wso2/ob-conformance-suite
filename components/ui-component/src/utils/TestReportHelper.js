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

  /* Pass/Fail count of the whole test plan */
  getTestSummary(testData) {
    const results = { passed: 0, failed: 0, rate: 0 };
    let curResult;
    for (const spec in testData) {
      curResult = this.getAPIResult(testData[spec], this);
      results.passed += curResult.passed;
      results.failed += curResult.failed;
    }
    if (results.passed + results.failed === 0) {
      results.rate = 0;
    } else {
      results.rate = (100 * (results.passed / (results.passed + results.failed))).toFixed(2);
    }
    return results;
  }

  /* Pass/Fail feature count of the API */
  getAPIResult(api, thisClass) {
    const results = { passed: 0, failed: 0};
    let curResult;
    api.forEach((feature) => {
      curResult = thisClass.getFeatureResult(feature, thisClass);
      results.passed += (curResult.failed === 0) * 1;
      results.failed += (curResult.failed > 0) * 1;
    });
    return results;
  }

  /* Pass/Fail scenario count of the feature */
  getFeatureResult(feature, thisClass) {
    const results = { passed: 0, failed: 0 };
    feature.elements.forEach((scenario) => {
      thisClass.getScenarioResult(scenario, thisClass) === true ? results.passed += 1 : results.failed += 1;
    });
    return results;
  }

  /* Pass/Fail status of the scenario (based on step results) */
  getScenarioResult(scenario, thisClass) {
    let status = true;
    scenario.steps.forEach((step) => {
      status = status && (thisClass.getStepResult(step) === 'passed');
    });
    return status;
  }

  getStepResult(step) {
    return step.result.status;
  }

  /* Pass/Fail status of the whole feature (based on scenario results) */
  getFeatureResultStatus(feature, thisClass) {
    const result = thisClass.getFeatureResult(feature, thisClass);
    if (result.failed === 0) {
      return { class: 'passed-feature', status: 'Passed' };
    }
    return { class: 'failed-feature', status: 'Failed' };
  }

  /* Number of features to be tested in the test plan */
  getFeatureCount(testPlan) {
    let count = 0;
    for (const api in testPlan.specifications) {
      for (const scenario in testPlan.specifications[api].features) {
        count += 1;
      }
    }
    return count;
  }
}
