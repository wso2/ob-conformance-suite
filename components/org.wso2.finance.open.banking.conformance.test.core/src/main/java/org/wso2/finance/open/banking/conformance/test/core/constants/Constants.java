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

package org.wso2.finance.open.banking.conformance.test.core.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Constants used by the application.
 */
public class Constants {

    //End Point names
    public static final String ATM_END_POINT = "Atm";
    public static final String BRANCH_END_POINT = "Branch";
    public static final String PRODUCT_END_POINT = "Product";

    //API Specs
    public static final String AUTH_API_SPEC = "Authorize";
    public static final String TOKEN_API_SPEC = "Token";
    public static final String ACCOUNTS_INFORMATION_API_SPEC = "AccountsInformation";
    public static final String OPEN_DATA_API_SPEC = "OpenData";

    //Open API json file paths
    public static final String OPEN_DATA_V_1_0_0_OPEN_API = "samples/com.wso2.finance.open.banking." +
            "conformance.tests.opendata/src/main/resources/schema/v1_0_0/open_data.json";
    public static final String ACCOUNT_INFORMATION_V_2_0_0_OPEN_API = "samples/com.wso2.finance.open.banking." +
            "conformance.tests.accountsinfromation/src/main/resources/schema/v2_0_0/accounts_information.json";
    public static final String AUTHORIZE_V_1_0_0_OPEN_API = "samples/com.wso2.finance.open.banking." +
            "conformance.tests.accountsinfromation/src/main/resources/schema/v1_0_0/authorize.json";
    public static final String TOKEN_V_1_0_0_OPEN_API = "samples/com.wso2.finance.open.banking." +
            "conformance.tests.accountsinfromation/src/main/resources/schema/v1_0_0/token.json";

    public static final List<String> AVAILABLE_VECOTRS = Collections
            .unmodifiableList(Arrays.asList("@Security", "@Data"));

}
