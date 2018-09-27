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

package com.wso2.finance.open.banking.conformance.api.interceptors;

import org.wso2.msf4j.Request;
import org.wso2.msf4j.Response;
import org.wso2.msf4j.interceptor.ResponseInterceptor;

/**
 * MSF4J Response Interceptor to append CORS Headers.
 */
public class CorsInterceptor implements ResponseInterceptor {

    private String headerName = "Origin";
    private String allowOriginHeader = "Access-Control-Allow-Origin";

    @Override
    public boolean interceptResponse(Request request, Response response) throws Exception {

        response.setHeader(allowOriginHeader, "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        if (request.getHeader(headerName) != null && !request.getHeader(headerName).isEmpty()) {
            response.setHeader(allowOriginHeader, request.getHeader(headerName));
        }
        return true;
    }
}
