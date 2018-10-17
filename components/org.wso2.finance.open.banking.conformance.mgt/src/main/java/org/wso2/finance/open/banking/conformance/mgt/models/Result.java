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
 * Model class representing the Result of a Report of a TestPlan.
 */
public class Result {

    private int line;
    private List<ScenarioResult> elements;
    private String name;
    private String description;
    private String id;
    private String keyword;
    private String uri;
    private List<Tag> tags;

    public Result(int line, List<ScenarioResult> elements, String name, String description,
                   String id, String keyword, String uri, List<Tag> tags) {

        this.line = line;
        this.elements = elements;
        this.name = name;
        this.description = description;
        this.id = id;
        this.keyword = keyword;
        this.uri = uri;
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
    public List<ScenarioResult> getElements(){
        return  elements;
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
    public String getKeyword() {

        return keyword;
    }

    /**
     * @return
     */
    public String getUri() {

        return uri;
    }

    /**
     * @return
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @return
     */
    public String getFeatureStatus(){
        String featureStatus = "passed";
        for (ScenarioResult element : elements){
            if (element.getScenarioStatus().equals("failed")) {
                featureStatus = "failed";
            }
        }
        return featureStatus;
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
        return "Result [line=" + line + ", name=" + name + "elements = " + elements + ", description=" + description
                + ", URI=" + uri +  "tags = " + tags + "]";
    }

}
