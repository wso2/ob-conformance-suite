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

package com.wso2.finance.open.banking.conformance.mgt.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Scenario")
public class Scenario {

    @XmlAttribute
    private String scenarioName;
    @XmlAttribute
    private String specName;
    @XmlAttribute
    private String specSection;


    public Scenario() {

    }

    /**
     * @param specName
     * @param specSection
     */
    public Scenario(String specName, String specSection, String scenarioName) {

        this.specName = specName;
        this.specSection = specSection;
        this.scenarioName = scenarioName;
    }

    /**
     * @return
     */
    public String getScenarioName() {

        return scenarioName;
    }

    /**
     * @return
     */
    public String getSpecName() {

        return specName;
    }

    /**
     * @return
     */
    public String getSpecSection() {

        return specSection;
    }


}
