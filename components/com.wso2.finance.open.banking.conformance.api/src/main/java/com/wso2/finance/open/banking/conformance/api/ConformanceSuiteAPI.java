/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wso2.finance.open.banking.conformance.api;

import com.google.gson.Gson;
import com.wso2.finance.open.banking.conformance.api.dao.SpecificationDAO;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0.0-SNAPSHOT
 */
@Path("/specifications")
public class ConformanceSuiteAPI {

    private Gson gson = new Gson();
    private SpecificationDAO  specificationDAO = new SpecificationDAO();

    @GET
    @Path("/all")
    public String get() {
        return gson.toJson(specificationDAO.getBasicSpecifications());
    }

    @GET
    @Path("/single/{name}")
    public String getSpecification(@PathParam("name") String name) {
        return gson.toJson(specificationDAO.getSpecification(name));
    }
}
