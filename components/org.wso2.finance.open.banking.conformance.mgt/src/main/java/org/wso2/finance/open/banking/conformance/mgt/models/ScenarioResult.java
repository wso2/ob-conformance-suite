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

import java.util.List;

/**
 * Model class representing the Result of a Scenario of a TestPlan.
 */
public class ScenarioResult {

    private int line;
    private String name;
    private String description;
    private String id;
    private String type;
    private String keyword;
    private List<StepResult> steps;
    private List<Tag> tags;

    public ScenarioResult(int line, String name, String description, String id, String type,
                  String keyword, List<StepResult> steps, List<Tag> tags) {

        this.line = line;
        this.name = name;
        this.description = description;
        this.id = id;
        this.type = type;
        this.keyword = keyword;
        this.steps = steps;
        this.tags = tags;
    }

    /**
     * @return
     */
    public int getLine() {

        return line;
    }

    /**
     * @return
     */
    public String getName() {

        return name;
    }

    /**
     * @return
     */
    public String getDescription() {

        return description;
    }

    /**
     * @return
     */
    public String getId() {

        return id;
    }

    /**
     * @return
     */
    public String getType() {

        return type;
    }

    /**
     * @return
     */
    public String getKeyword() {

        return keyword;
    }

    /**
     * @return
     */
    public List<StepResult> getSteps(){
        return steps;
    }

    /**
     * @return
     */
    public  List<Tag> getTags() {
        return tags;
    }

    /**
     * @return
     */
    public String getScenarioStatus(){
        String scenarioStatus = "passed";
        for (StepResult step : steps){
            if (step.getStatus().equals("failed") ) {
                scenarioStatus = "failed";
            }
        }
        return scenarioStatus;
    }

    public static class Tag{
        private String name;

        public Tag(String name){
            this.name = name;
        }

        /**
         * @return
         */
        public  String getTagName() {
            return name;
        }

        @Override
        public String toString() {
            return "Tag [name=" + name + "]";
        }
    }

    @Override
    public String toString() {
        return "ScenarioResult [line=" + line + ", name=" + name + ", description=" + description + ", id=" + id +
               ", type =" + type + ", keywords=" + keyword + "steps = " + steps + "tags = " + tags + "]";
    }
}
