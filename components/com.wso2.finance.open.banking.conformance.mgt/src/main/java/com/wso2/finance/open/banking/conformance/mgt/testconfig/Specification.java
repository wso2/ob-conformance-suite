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

package com.wso2.finance.open.banking.conformance.mgt.testconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO representing the front end Specification object.
 */
public class Specification {

    private String name;
    private String version;
    private Map<String, Feature> features;  //uri->feature
    private Map<String, Map<String, String>> attributeGroups;
    private List<String> testingVectors;

    public Specification() {

    }

    public Specification(String name, String version, Map<String, Feature> features,
                         Map<String, Map<String, String>> attributeGroups, List<String> testingVectors) {

        this.name = name;
        this.version = version;
        this.features = features;
        this.attributeGroups = attributeGroups;
        this.testingVectors = testingVectors;
    }

    public String getName() {

        return name;
    }

    public String getVersion() {

        return version;
    }

    public List<Feature> getFeatures() {

        return new ArrayList(features.values());
    }

    public String getAttribute(String attributeGroup, String attributeName) {

        return attributeGroups.get(attributeGroup).get(attributeName);
    }

    public Feature getFeature(String key) {

        return features.get(key);
    }
}
