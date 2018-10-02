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


export default class TestPlanReduxHelper {
    static getSpecFromState(state, specName) {
        return state.specs[specName];
    }

    static getSelectedVectorsFromState(state, specName) {
        return this.getSpecFromState(state, specName).selectedVectors;
    }

    static getSelectedFeaturesFromState(state, specName) {
        return this.getSpecFromState(state, specName).selectedFeatures;
    }

    static getSelectedSpecsFromState(state, selectedFeatures) {
        return Object.values(state.specs).filter(spec => selectedFeatures.includes(spec.name));
    }

    static buildTestPlanSpecFromTestValues(spec) {
        const selectedFeatures = {};

        spec.selectedFeatures.forEach(key => selectedFeatures[key] = {
            uri: key,
            attributeGroups: spec.selectedValues.features[key],
        });

        return {
            name: spec.name,
            version: spec.version,
            features: selectedFeatures,
            testingVectors: spec.selectedVectors,
            attributeGroups: spec.selectedValues.specification,
        };
    }


    static buildTestPlanFromTestValues(testvalues) {
        const specs = {};
        Object.keys(testvalues.specs).forEach((key) => {
            specs[key] = TestPlanReduxHelper.buildTestPlanSpecFromTestValues(testvalues.specs[key]);
        });
        return {
            name: testvalues.name,
            specifications: specs,
        };
    }
}
