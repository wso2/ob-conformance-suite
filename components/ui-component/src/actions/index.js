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

export const addSpecification = (name, spec) => ({
    type: 'ADD_SPEC',
    name,
    specification: spec,
});

export const updateSpecification = (name, spec) => ({
    type: 'UPDATE_SPEC',
    name,
    specification: spec,
});

export const toggleSpecification = name => ({
    type: 'TOGGLE_SPEC',
    name,
});

export const clearSelectedSpecifications = () => ({
    type: 'CLEAR_SELECTED_SPECS',
});

export const clearSpecifications = () => ({
    type: 'CLEAR_SPECS',
});

export const addSpecificationToTestValues = spec => ({
    type: 'ADD_SPEC_TO_TESTVALUES',
    specification: spec,
});

export const toggleVector = (name, vector) => ({
    type: 'TOGGLE_VECTOR',
    name,
    vector,
});

export const toggleFeature = (name, feature) => ({
    type: 'TOGGLE_FEATURE',
    name,
    feature,
});

export const setSpecValue = (specName, groupName, attributeName, value) => ({
    type: 'SET_VALUE_SPEC',
    specName,
    groupName,
    attributeName,
    value,
});

export const setFeatureValue = (specName, featureName, groupName, attributeName, value) => ({
    type: 'SET_VALUE_FEATURE',
    specName,
    featureName,
    groupName,
    attributeName,
    value,
});

export const setTestName = name => ({
    type: 'SET_NAME_TO_TESTVALUES',
    name,
});


export const clearTestValues = () => ({
    type: 'CLEAR_TESTVALUES',
});

export const addTestPlan = (id, testplan, reports) => ({
    type: 'ADD_TESTPLAN',
    id,
    testplan,
    reports,
});

export const clearTestPlan = () => ({
    type: 'CLEAR_TESTPLAN',
});

export const updateReport = report => ({
    type: 'UPDATE_REPORT',
    report,
});
