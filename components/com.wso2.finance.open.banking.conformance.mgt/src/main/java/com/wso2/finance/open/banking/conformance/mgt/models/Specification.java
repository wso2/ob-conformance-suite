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
 * Model class representing a single Specification.
 */
@XmlRootElement(namespace = "com.wso2.finance.open.banking.conformance.mgt.models", name = "Specification")
public class Specification {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private String version;
    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlElement
    private String specificationUri;
    @XmlElement
    private String glueClassPath;
    @XmlElementWrapper(name = "attributeGroups")
    @XmlElement(name = "AttributeGroup")
    private List<AttributeGroup> attributeGroups;
    @XmlElementWrapper(name = "vectors")
    @XmlElement(name = "Vector")
    private List<Vector> testingVectors;
    @XmlElementWrapper(name = "features")
    @XmlElement(name = "Feature")
    private List<Feature> features;

    public Specification() {

    }

    public Specification(String name, String version, String title, String description, String specificationUri) {

        this.name = name;
        this.version = version;
        this.title = title;
        this.description = description;
        this.specificationUri = specificationUri;
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
    public String getSpecificationUri() {

        return specificationUri;
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
    public List<Vector> getTestingVectors() {

        return testingVectors;
    }

    /**
     * @return
     */
    public List<Feature> getFeatures() {

        return features;
    }

    /**
     * @return
     */
    public String getVersion() {

        return version;
    }

    public String getGlueClassPath() {

        return glueClassPath;
    }
}
