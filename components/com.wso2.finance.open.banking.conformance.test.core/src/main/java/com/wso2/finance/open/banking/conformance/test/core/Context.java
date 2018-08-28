
/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package com.wso2.finance.open.banking.conformance.test.core;

public class Context {

    private static Context context = new Context();

    private  String baseURL;
    private  String bankID;
    private  String basePath;

    private Context(){
        baseURL = "https://api-openbanking.wso2.com/OpenBankAPI";
        bankID = "bank-4020-01";
        basePath = "v1.0.0/banks/bank-4020-01/atms";

    }

    public static Context getInstance(){
        return context;
    }

    public  String getBaseURL() { return baseURL;}

    public String getBasePath(){return basePath;}

    public String getBankID(){
        return bankID;
    }

}
