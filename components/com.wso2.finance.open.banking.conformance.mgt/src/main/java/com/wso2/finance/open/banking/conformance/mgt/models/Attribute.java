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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class representing attributes required for a Specification or Feature.
 */
@XmlRootElement(name = "Attribute")
public class Attribute {

    @XmlAttribute
    String name;
    @XmlElement
    String label;
    @XmlElement
    ATTRIBUTE_TYPE attributeType;
    @XmlElement
    String defaultValue;
    @XmlElement
    String value;
    @XmlElement
    String helpText;
    @XmlElement
    String validationRegex;

    public enum ATTRIBUTE_TYPE {
        String
    }

    public Attribute() {

    }

    /**
     * @param name
     * @param label
     * @param attributeType
     * @param defaultValue
     * @param helpText
     */
    public Attribute(String name, String label, ATTRIBUTE_TYPE attributeType, String defaultValue, String value, String helpText) {

        this.name = name;
        this.label = label;
        this.attributeType = attributeType;
        this.defaultValue = defaultValue;
        this.value = value;
        this.helpText = helpText;
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
    public String getLabel() {

        return label;
    }

    /**
     * @return
     */
    public ATTRIBUTE_TYPE getAttributeType() {

        return attributeType;
    }

    /**
     * @return
     */
    public String getDefaultValue() {

        return defaultValue;
    }

    /**
     * @return
     */
    public String gettValue() {

        return value;
    }

    /**
     * @return
     */
    public String getHelpText() {

        return helpText;
    }
}
