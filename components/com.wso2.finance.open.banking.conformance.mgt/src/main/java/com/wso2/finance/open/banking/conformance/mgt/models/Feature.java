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

package com.wso2.finance.open.banking.conformance.mgt.models;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class representing a test feature.
 */
@XmlRootElement(name = "Feature")
public class Feature {

    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlAttribute
    private File uri;
    @XmlElementWrapper(name = "attributeGroups")
    @XmlElement(name = "AttributeGroup")
    private List<AttributeGroup> attributeGroups;
    @XmlElementWrapper(name = "Scenarios")
    @XmlElement(name = "Scenario")
    private List<Scenario> scenarios;

    public Feature() {

    }

    /**
     * @param title
     * @param description
     * @param uri
     * @param attributeGroups
     */
    public Feature(String title, String description, File uri, List<AttributeGroup> attributeGroups, List<Scenario> scenarios) {

        this.title = title;
        this.description = description;
        this.uri = uri;
        this.attributeGroups = attributeGroups;
        this.scenarios = scenarios;
    }

    /**
     * @return
     */
    public String getTitle() {

        return title;
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
    public File getUri() {

        return uri;
    }

    /**
     * @return
     */
    public List<AttributeGroup> getAttributeGroups() {

        return attributeGroups;
    }

    /**
     * @return
     */
    public List<Scenario> getScenarios() {

        return scenarios;
    }

    /**
     * Get specific AttributeGroup by name
     *
     * @param groupName
     * @return
     * @throws NoSuchElementException
     */
    public AttributeGroup getAttributeGroup(String groupName) throws NoSuchElementException {

        int len = attributeGroups.size();
        for (int i = 0; i < len; i++) {
            if (attributeGroups.get(i).getGroupName().equals(groupName)) {
                return attributeGroups.get(i);
            }
        }
        throw new NoSuchElementException("Invalid group name.");
    }
}
