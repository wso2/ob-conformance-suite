/*
 *
 *  * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *  *
 *  * WSO2 Inc. licenses this file to you under the Apache License,
 *  * Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.wso2.finance.open.banking.conformance.api.interceptors.abstractions;

import java.nio.charset.Charset;
import java.util.Base64;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response.Status;
import org.wso2.msf4j.Request;
import org.wso2.msf4j.Response;
import org.wso2.msf4j.interceptor.RequestInterceptor;

/**
 * Abstract Basic Authentication security Interceptor.
 */
public abstract class AbstractBasicAuthSecurityInterceptor implements RequestInterceptor {

    private static final int AUTH_TYPE_BASIC_LENGTH = "Basic".length();

    public AbstractBasicAuthSecurityInterceptor() {

    }

    /**
     * Intercept method used to authenticate the user.
     *
     * @param request request
     * @param response response
     * @return
     * @throws Exception
     */
    public boolean interceptRequest(Request request, Response response) throws Exception {

        String authHeader = request.getHeader("Authorization");

        if (request.getHttpMethod().equals(HttpMethod.OPTIONS)) {
            return true;
        }

        if (authHeader != null) {
            String authType = authHeader.substring(0, AUTH_TYPE_BASIC_LENGTH);
            String authEncoded = authHeader.substring(AUTH_TYPE_BASIC_LENGTH).trim();
            if ("Basic".equals(authType) && !authEncoded.isEmpty()) {
                byte[] decodedByte = authEncoded.getBytes(Charset.forName("UTF-8"));
                String authDecoded = new String(Base64.getDecoder().decode(decodedByte), Charset.forName("UTF-8"));
                String[] authParts = authDecoded.split(":");
                String username = authParts[0];
                String password = authParts[1];
                if (this.authenticate(username, password)) {
                    return true;
                }
            }
        }

        response.setStatus(Status.UNAUTHORIZED.getStatusCode());
        response.setHeader("WWW-Authenticate", "Basic");
        return false;
    }

    protected abstract boolean authenticate(String var1, String var2);
}

