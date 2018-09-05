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

export const addSpecification = (name,spec) => ({
    type: 'ADD_SPEC',
    name: name,
    specification: spec
});

export const updateSpecification = (name,spec) => ({
    type: 'UPDATE_SPEC',
    name: name,
    specification: spec
});

export const toggleSpecification = name => ({
    type: 'TOGGLE_SPEC',
    name: name
});

export const clearSpecifications = () => ({
    type: 'CLEAR_SPECS',
});

export const addSpecificationToTestValues = (spec) => ({
    type: 'ADD_SPEC_TO_TESTVALUES',
    specification: spec
});

export const clearTestPlan = () => ({
    type: 'CLEAR_TESTPLAN',
});

export const toggleVector = (name,vector) => ({
    type: 'TOGGLE_VECTOR',
    name: name,
    vector: vector
});

export const toggleFeature = (name,feature) => ({
    type: 'TOGGLE_FEATURE',
    name: name,
    feature: feature
});

export const setSpecValue = (specName,groupName,attributeName,value) => ({
    type: 'SET_VALUE_SPEC',
    specName: specName,
    groupName: groupName,
    attributeName: attributeName,
    value: value
});

export const setFeatureValue = (specName,featureName,groupName,attributeName,value) => ({
    type: 'SET_VALUE_FEATURE',
    specName: specName,
    featureName: featureName,
    groupName: groupName,
    attributeName: attributeName,
    value: value
});