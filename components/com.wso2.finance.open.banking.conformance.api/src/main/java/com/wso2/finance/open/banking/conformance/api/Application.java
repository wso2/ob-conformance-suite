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

import com.wso2.finance.open.banking.conformance.api.interceptors.CorsInterceptor;
import com.wso2.finance.open.banking.conformance.mgt.helpers.XmlHelper;
import com.wso2.finance.open.banking.conformance.mgt.models.Specification;
import org.apache.log4j.Logger;
import org.wso2.msf4j.MicroservicesRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 * Application main entry point.
 * Load Necessary resource XML's and start the Microservices.
 *
 * @since 1.0.0-SNAPSHOT
 */
public class Application {

    private static Logger log = Logger.getLogger(Application.class);

    /**
     * Temporary resource loader.
     */
    private static void loadResources() {

        try {
            Map<String, Specification> specifications = new HashMap();

            specifications.put("OpenData", XmlHelper.unmarshallSepcificationXML(
                    new File("components/" +
                            "com.wso2.finance.open.banking.conformance.mgt/src/main/resources/openData.xml")));
            specifications.put("AccountsInformation", XmlHelper.unmarshallSepcificationXML(
                    new File("components/" +
                            "com.wso2.finance.open.banking.conformance.mgt/src/main/resources/" +
                            "accountsinformation.xml")));

            ApplicationDataHolder.getInstance().setSpecifications(specifications);

        } catch (JAXBException e) {
            log.error("Unable to load XML Resources", e);
        }
    }

    public static void main(String[] args) {

        loadResources();
        new MicroservicesRunner()
                .deploy(new ConformanceSuiteAPI())
                .deploy(new TestPlanAPI())
                .deploy(new ResultsAPI())
                .addGlobalResponseInterceptor(new CorsInterceptor())
                .start();
    }
}
