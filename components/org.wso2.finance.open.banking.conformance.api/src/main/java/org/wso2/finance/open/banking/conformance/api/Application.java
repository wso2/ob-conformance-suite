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

package org.wso2.finance.open.banking.conformance.api;

import org.apache.log4j.Logger;
import org.wso2.finance.open.banking.conformance.api.interceptors.CorsInterceptor;
import org.wso2.finance.open.banking.conformance.api.interceptors.SecurityInterceptor;
import org.wso2.finance.open.banking.conformance.api.services.OptionsAPI;
import org.wso2.finance.open.banking.conformance.api.services.ResultsAPI;
import org.wso2.finance.open.banking.conformance.api.services.RunnerManagerAPI;
import org.wso2.finance.open.banking.conformance.api.services.SpecificationAPI;
import org.wso2.finance.open.banking.conformance.api.services.TestPlanAPI;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.helpers.XmlHelper;
import org.wso2.finance.open.banking.conformance.mgt.models.Specification;
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
                    new File("samples/" +
                            "org.wso2.finance.open.banking.conformance.tests.opendata/src/main/resources/config.xml")));

            specifications.put("AccountsInformation", XmlHelper.unmarshallSepcificationXML(
                    new File("samples/" +
                            "org.wso2.finance.open.banking.conformance.tests.accountsinfromation/" +
                            "src/main/resources/config.xml")));

            ApplicationDataHolder.getInstance().setSpecifications(specifications);

        } catch (JAXBException e) {
            log.error("Unable to load XML Resources", e);
        }
    }

    public static void main(String[] args) {

        loadResources();
        DBConnector.createTablesIfNotExist();
        new MicroservicesRunner()
                .deploy(new SpecificationAPI())
                .deploy(new TestPlanAPI())
                .deploy(new ResultsAPI())
                .deploy(new RunnerManagerAPI())
                .deploy(new OptionsAPI())
                .addGlobalRequestInterceptor(new SecurityInterceptor())
                .addGlobalResponseInterceptor(new CorsInterceptor())
                .start();
    }
}
