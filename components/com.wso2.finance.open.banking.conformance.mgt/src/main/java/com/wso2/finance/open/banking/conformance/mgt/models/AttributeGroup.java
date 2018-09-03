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

import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Groups Attributes Logically.
 */
@XmlRootElement(name = "AttributeGroup")
public class AttributeGroup {

    @XmlAttribute
    private String groupName;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String description;
    @XmlElement(name = "Attribute")
    private List<Attribute> attributes;

    public AttributeGroup() {

    }

    /**
     * @param groupName
     * @param title
     * @param description
     * @param attributes
     */
    public AttributeGroup(String groupName, String title, String description, List<Attribute> attributes) {

        this.groupName = groupName;
        this.title = title;
        this.description = description;
        this.attributes = attributes;
    }

    /**
     * @return
     */
    public String getGroupName() {

        return groupName;
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
    public List<Attribute> getAttributes() {

        return attributes;
    }

    /**
     *
     * @param name
     * @return
     * @throws NoSuchElementException
     */
    public Attribute getAttribute(String name) throws NoSuchElementException {
        int len=attributes.size();
        for(int i=0; i<len; i++) {
            if (attributes.get(i).getName().equals(name)){
                return attributes.get(i);
            }
        }
        throw new NoSuchElementException("Invalid attribute name.");
    }
}
