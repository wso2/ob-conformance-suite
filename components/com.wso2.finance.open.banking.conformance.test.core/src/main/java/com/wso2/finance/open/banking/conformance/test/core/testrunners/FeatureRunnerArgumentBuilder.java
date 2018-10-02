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

import com.wso2.finance.open.banking.conformance.test.core.constants.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Build Argument String for Cucumber.
 */
public class FeatureRunnerArgumentBuilder {

    private List<String> arguments;
    private String featureUri = "";

    public FeatureRunnerArgumentBuilder() {

        arguments = new ArrayList<>();
    }

    public FeatureRunnerArgumentBuilder addResultFile(File location) {

        arguments.add("-p");
        arguments.add("json:" + location.getPath());
        return this;
    }

    public FeatureRunnerArgumentBuilder addSteps(String classpath) {

        arguments.add("-g");
        arguments.add(classpath);
        return this;

    }

    public FeatureRunnerArgumentBuilder addTag(String tag) {

        arguments.add("-t");
        arguments.add(tag);
        return this;
    }

    public FeatureRunnerArgumentBuilder addFeature(String uri) {

        this.featureUri = uri;
        return this;
    }

    public FeatureRunnerArgumentBuilder addTags(List<String> currentTestingVectors) {

        List<String> vectors = new ArrayList<>(Constants.AVAILABLE_VECOTRS);

        // Remove Vector if selected
        for (String vector : currentTestingVectors){
            vectors.remove(vector.indexOf(vector));
        }

        // format tag to be inverted.
        List<String> formattedVectors = vectors.stream()
                .map(vector -> ("(not " + vector + ") ")).collect(Collectors.toList());

        if (formattedVectors.size() != 0) {
            arguments.add("-t");
            arguments.add(String.join(" or ", formattedVectors));
        }
        return this;
    }

    public String[] build() {

        arguments.add(featureUri);
        System.out.println(String.join(" ", arguments));
        return arguments.toArray(new String[arguments.size()]);
    }
}
