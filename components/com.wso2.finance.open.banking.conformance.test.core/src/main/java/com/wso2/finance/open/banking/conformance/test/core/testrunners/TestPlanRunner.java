/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPlanRunner {

   private TestPlan testPlan;

   public TestPlanRunner(TestPlan plan)
   {
       this.testPlan = plan;
   }

   public Map<String,List<JsonObject>> runTestPlan(){

      Map<String,List<JsonObject>> results = new HashMap();
      Gson gson = new Gson();

      for(Specification spec : testPlan.getSpecifications())
      {
         Log.info("Start Running TestPlan");
         //set current spec and version to the context if necessary
         SpecRunner specRunner = new SpecRunner(spec);
         results.put(spec.getName(),specRunner.runSpecification());
         //clear context
         Log.info("End Running TestPlan");
      }
      return results;
   }

}
