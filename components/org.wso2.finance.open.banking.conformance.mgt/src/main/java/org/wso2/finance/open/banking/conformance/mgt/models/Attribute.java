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
    AttributeType attributeType;
    @XmlElement
    String defaultValue;
    @XmlElement
    String value;
    @XmlElement
    String helpText;
    @XmlElement
    String validationRegex;

    /**
     * Enum representing the type of the Attribute.
     */
    public enum AttributeType {
        String, TextLabel, LinkButton
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
    public Attribute(String name, String label, AttributeType attributeType, String defaultValue,
                     String value, String helpText) {

        this.name = name;
        this.label = label;
        this.attributeType = attributeType;
        this.defaultValue = defaultValue;
        this.value = value;
        this.helpText = helpText;
    }

    /**
     * @return name of the attribute
     */
    public String getName() {

        return name;
    }

    /**
     * @return label
     */
    public String getLabel() {

        return label;
    }

    /**
     * @return type of the attribute
     */
    public AttributeType getAttributeType() {

        return attributeType;
    }

    /**
     * @return default value of the attribute
     */
    public String getDefaultValue() {

        return defaultValue;
    }

    /**
     * @return value of the attribute
     */
    public String gettValue() {

        return value;
    }

    /**
     * @return help text highlighting meaning
     */
    public String getHelpText() {

        return helpText;
    }
}
