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

package org.wso2.finance.open.banking.conformance.api.dao;

import org.wso2.finance.open.banking.conformance.api.ApplicationDataHolder;
import org.wso2.finance.open.banking.conformance.api.dto.BasicSpecificationDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.Specification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DTO for getting information on Specifications.
 */
public class SpecificationDAO {

    private Map<String, Specification> specifications = null;

    public SpecificationDAO() {

        this.specifications = ApplicationDataHolder.getInstance().getSpecifications();
    }

    /**
     * @return List of basic specifications
     */
    public List<BasicSpecificationDTO> getBasicSpecifications() {

        return this.specifications.values().stream().map(specification ->
                new BasicSpecificationDTO(specification.getName(),
                        specification.getTitle(), specification.getVersion(),
                        specification.getDescription(), specification.getSpecificationUri())
        ).collect(Collectors.toList());
    }

    /**
     * @param key name of the spec
     * @return specification for the given name
     */
    public Specification getSpecification(String key) {

        return this.specifications.get(key);
    }
}
