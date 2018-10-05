
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

package com.wso2.finance.open.banking.conformance.test.core.request;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Helper class for generating API Requests.
 */
public class RequestGenerator {

    private static Logger log = Logger.getLogger(RequestGenerator.class);

    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();

    protected RequestSpecBuilder addHeader(String name, String value) {

        return requestBuilder.addHeader(name, value);
    }

    protected RequestSpecBuilder addFilter(Filter filter) {

        return requestBuilder.addFilter(filter);
    }

    protected RequestSpecBuilder setBaseUri(String uri) {

        return requestBuilder.setBaseUri(uri);
    }

    protected RequestSpecBuilder setPort(int port) {

        return requestBuilder.setPort(port);
    }

    protected RequestSpecBuilder setBody(String body) {

        return requestBuilder.setBody(body);
    }

    protected RequestSpecBuilder setAccept(String accept) {

        return requestBuilder.setAccept(accept);
    }

    protected RequestSpecBuilder setContenType(String contentType) {

        return requestBuilder.setContentType(contentType);
    }

    protected RequestSpecification build() {

        return requestBuilder.build();
    }

    protected RequestSpecBuilder setAuth(AuthenticationScheme authScheme) {

        return requestBuilder.setAuth(authScheme);
    }

    public RequestSpecification generate() {

        log.debug("Generating default http request");

        String swaggerJsonFile = Context.getInstance().getCurrentSwaggerJsonFile();

        SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(swaggerJsonFile);

        String baseUri = Context.getInstance()
                .getCurrentSpecAttribute("default", "base_url");

        return this.addFilter(validationFilter).
                setBaseUri(baseUri).
                setAccept("application/json").build();

    }

}
