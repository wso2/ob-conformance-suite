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

import java.util.Map;

/**
 * DTO representing the front end Feature object.
 */
public class Feature {

    private String title;
    private String uri;
    private Map<String, Map<String, String>> attributeGroups;

    public Feature() {

    }

    public Feature(String title, Map<String, Map<String, String>> attributeGroups) {

        this.title = title;
        this.attributeGroups = attributeGroups;
    }

    public String getTitle() {

        return title;
    }

    public String getUri() {

        return uri;
    }

    public String getAttribute(String attributeGroup, String attributeName) {

        return attributeGroups.get(attributeGroup).get(attributeName);
    }
}
