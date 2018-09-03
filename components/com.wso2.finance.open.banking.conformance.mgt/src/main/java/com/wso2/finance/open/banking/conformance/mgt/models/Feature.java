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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class representing a test feature
 */
@XmlRootElement(name = "Feature")
public class Feature {

    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlAttribute
    private File uri;
    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "Attribute")
    private List<Attribute> attributes;

    public Feature() {

    }

    public Feature(String title, String description, File uri, List<Attribute> attributes) {

        this.title = title;
        this.description = description;
        this.uri = uri;
        this.attributes = attributes;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public File getUri() {

        return uri;
    }

    public List<Attribute> getAttributes() {

        return attributes;
    }
}
