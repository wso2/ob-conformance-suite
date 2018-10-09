
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

package org.wso2.finance.open.banking.conformance.test.core.request;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.wso2.finance.open.banking.conformance.test.core.context.Context;

/**
 * Helper class for generating API Requests.
 */
public class RequestGenerator {

    private static Logger log = Logger.getLogger(RequestGenerator.class);

    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();

    /**
     * @param name  header parameter name
     * @param value header parameter value
     * @return RequestGenerator
     */
    protected RequestGenerator addHeader(String name, String value) {

        requestBuilder.addHeader(name, value);
        return this;
    }

    /**
     * @param filter RestAssured filter
     * @return RequestGenerator
     */
    protected RequestGenerator addFilter(Filter filter) {

        requestBuilder.addFilter(filter);
        return this;
    }

    /**
     * @param uri base url of the HTTP Request
     * @return RequestGenerator
     */
    protected RequestGenerator setBaseUri(String uri) {

        requestBuilder.setBaseUri(uri);
        return this;
    }

    /**
     * @param port service port
     * @return RequestGenerator
     */
    protected RequestGenerator setPort(int port) {

        requestBuilder.setPort(port);
        return this;
    }

    /**
     * @param body HTTP Request body string
     * @return RequestGenerator
     */
    protected RequestGenerator setBody(String body) {

        requestBuilder.setBody(body);
        return this;
    }

    /**
     * @param accept Accept Header Field
     * @return RequestGenerator
     */
    protected RequestGenerator setAccept(String accept) {

        requestBuilder.setAccept(accept);
        return this;
    }

    /**
     * @param contentType contentType Header Field
     * @return RequestGenerator
     */
    protected RequestGenerator setContentType(String contentType) {

        requestBuilder.setContentType(contentType);
        return this;
    }

    /**
     * @param contentType Content-Type Header Field
     * @return RequestGenerator
     */
    protected RequestGenerator setContenType(String contentType) {

        requestBuilder.setContentType(contentType);
        return this;
    }

    /**
     * @param authScheme authentication scheme used to communicate with server
     * @return RequestGenerator
     */
    protected RequestGenerator setAuth(AuthenticationScheme authScheme) {

        requestBuilder.setAuth(authScheme);
        return this;
    }

    /**
     * @return RequestSpecification
     */
    protected RequestSpecification build() {

        return requestBuilder.build();
    }

    /**
     * Generates the Request Specification.
     *
     * @return RequestSpecification
     */
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
