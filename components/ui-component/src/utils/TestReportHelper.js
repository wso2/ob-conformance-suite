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

     getTestSummary(testData) {
         var results = {passed : 0, failed : 0, rate: 0}
         var passed = 0;
         var failed = 0;

         for (var specName in testData) {
             var specArray = testData[specName];
             for (var spec in specArray) {
                 var elementArray = specArray[spec]['elements'];
                 for (var element in elementArray){
                     var stepArray = elementArray[element]["steps"];
                     var status = true;
                     for (var result in stepArray){
                         var curStatus = stepArray[result]['result']['status']

                         status = status && (curStatus === "passed");
                     }
                     status ? results.passed += 1 : results.failed +=1
                 }
             }
         }
         results.rate = (100*(results.passed/(results.passed+results.failed))).toFixed(2);
         return results
    }
}
