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

package com.wso2.finance.open.banking.conformance.api;

import com.wso2.finance.open.banking.conformance.mgt.models.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds common objects for the API
 */
public class ApplicationDataHolder {

    private static ApplicationDataHolder instance = new ApplicationDataHolder();
    private Map<String,Specification> specifications = null;

    private ApplicationDataHolder(){

    }

    public static ApplicationDataHolder getInstance() {

        return instance;
    }

    public Map<String,Specification> getSpecifications() {

        return specifications;
    }

    public void setSpecifications(Map<String,Specification> specifications) {

        this.specifications = specifications;
    }
}
