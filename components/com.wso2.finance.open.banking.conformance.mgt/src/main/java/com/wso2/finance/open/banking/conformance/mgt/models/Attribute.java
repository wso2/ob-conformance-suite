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
 * Model class representing attributes required for a Specification or Feature
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
    String helpText;

    public enum ATTRIBUTE_TYPE {
        URL, TEXT, NUMBER
    }

    public Attribute() {

    }

    public Attribute(String name, String label, ATTRIBUTE_TYPE attributeType, String defaultValue, String helpText) {

        this.name = name;
        this.label = label;
        this.attributeType = attributeType;
        this.defaultValue = defaultValue;
        this.helpText = helpText;
    }

    public String getName() {

        return name;
    }

    public String getLabel() {

        return label;
    }

    public ATTRIBUTE_TYPE getAttributeType() {

        return attributeType;
    }

    public String getDefaultValue() {

        return defaultValue;
    }

    public String getHelpText() {

        return helpText;
    }
}
