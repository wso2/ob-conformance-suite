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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class representing a single Specification
 */
@XmlRootElement(namespace = "com.wso2.finance.open.banking.conformance.mgt.models", name = "Specification")
public class Specification {

    @XmlAttribute
    private String name;
    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlElement
    private String specificationUri;
    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "Attribute")
    private List<Attribute> attributes;
    @XmlElementWrapper(name = "features")
    @XmlElement(name = "Feature")
    private List<Feature> features;

    public Specification() {

    }

    public Specification(String name, String title, String description, String specificationUri, List<Attribute> attributes, List<Feature> features) {

        this.name = name;
        this.title = title;
        this.description = description;
        this.specificationUri = specificationUri;
        this.attributes = attributes;
        this.features = features;
    }

    public String getName() {

        return name;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public String getSpecificationUri() {

        return specificationUri;
    }

    public List<Attribute> getAttributes() {

        return attributes;
    }

    public List<Feature> getFeatures() {

        return features;
    }
}
