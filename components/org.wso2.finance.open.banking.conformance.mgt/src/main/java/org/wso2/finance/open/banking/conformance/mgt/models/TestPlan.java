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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class representing the test plan.
 */
@XmlRootElement(name = "TestPlan")
public class TestPlan {

    @XmlElement(name = "Specification")
    private List<Specification> specifications;

    public TestPlan() {

    }

    /**
     * @param specifications
     */
    public TestPlan(List<Specification> specifications) {

        this.specifications = specifications;
    }

    /**
     * @return
     */
    public List<Specification> getSpecifications() {

        return specifications;
    }
}
