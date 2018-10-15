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

import java.util.Map;

/**
 * Model class representing the Result of a Step of a TestPlan.
 */
public class StepResult {

    private Map<String, String> result;
    private int line;
    private String name;
    private Map<String, String> match;
    private String keyword;

    public StepResult(Map<String, String> result, int line, String name, Map<String, String> match, String keyword){
        this.result = result;
        this.line = line;
        this.name = name;
        this.match = match;
        this.keyword = keyword;
    }

    /**
     * @return
     */
    public Map<String, String> getresults() {

        return result;
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
    public Map<String, String> getMatch() {

        return match;
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
    public String getStatus(){
        return result.get("status");
    }

    @Override
    public String toString() {
        return "StepResult [results =" + result + "line =" + line + "name =" + name + "match =" + match + "keyword =" + keyword +"]";
    }

}