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

package com.wso2.finance.open.banking.conformance.test.core.testrunners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import cucumber.api.cli.Main;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Execute a single Feature and return result.
 */
public class FeatureRunner {

    private Feature feature;

    private Logger log = Logger.getLogger(FeatureRunner.class);

    /**
     * @param feature
     */
    public FeatureRunner(Feature feature) {

        this.feature = feature;
    }

    /**
     * Run each Scenario in a feature.
     *
     * @return
     */
    public JsonObject runFeature() {

        log.debug("Start Running Feature: " + feature.getTitle());

        Context.getInstance().setFeatureContext(feature.getUri());
        File resultFile = new File("target/cucumber-report/cucumber.json");

        String[] argv = new FeatureRunnerArgumentBuilder().addResultFile(resultFile)
                .addSteps("classpath:com.wso2.finance.open.banking.conformance.tests")
                .addTags(Context.getInstance().getCurrentTestingVectors())
                .addFeature(feature.getUri())
                .build();

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Main.run(argv, contextClassLoader);
        } catch (Exception e) {
            log.error("Unable to Run Feature " + feature.getTitle(), e);
        }

        Context.getInstance().clearFeatureContext();

        log.debug("End Running Feature: " + feature.getTitle());

        try {
            return this.readJson(resultFile);
        } catch (Exception e) {
            log.error("Feature Result File Not Found", e);
            return null;
        }
    }

    /**
     * @param url
     * @return
     * @throws FileNotFoundException
     */
    private JsonObject readJson(File url) throws UnsupportedEncodingException, FileNotFoundException {

        JsonParser parser = new JsonParser();

        Reader reader = new InputStreamReader(new FileInputStream(url), "UTF-8");

        JsonElement jsonElement = parser.parse(reader);
        if ((jsonElement.getAsJsonArray().size() != 0) &&
                (jsonElement.getAsJsonArray().get(0) != null)) {

            return jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        } else {
            return new JsonObject();
        }
    }
}
