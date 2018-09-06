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

const initialState = {
    specs: {}
};

function generateValueMap(attributeGroups) {
    var groupMap = {};
    attributeGroups.forEach((group) => {
        var attrMap = {};
        group.attributes.forEach((attr) => {
            attrMap[attr.name] = (attr.defaultValue ? attr.defaultValue : null);
        });
        groupMap[group.groupName] = attrMap;
    });
    return groupMap
}

function generateFeaturesValueMap(features){
    var featureMap = {};
    features.forEach((feature) => {
        featureMap[feature.uri.path] = (feature.attributeGroups ? generateValueMap(feature.attributeGroups) : {})
    })
    return featureMap;
}

function initSpec(spec) {
    return {
        name: spec.name,
        version: spec.version,
        selectedVectors: spec.testingVectors.map((vector => vector.tag)),
        selectedFeatures: spec.features.map((feature => feature.uri.path)),
        selectedValues: {
            specification : (spec.attributeGroups ? generateValueMap(spec.attributeGroups) : {}),
            features : (generateFeaturesValueMap(spec.features))
        }
    }
}


const testvalues = (state = initialState, action) => {
    switch (action.type) {
        case 'ADD_SPEC_TO_TESTVALUES':
            return {
                specs: {
                    ...state.specs,
                    [action.specification.name]: initSpec(action.specification)
                }
            };
        case 'TOGGLE_VECTOR':
            var cur_vectors = state.specs[action.name].selectedVectors;
            return {
                specs: {
                    ...state.specs,
                    [action.name]: {
                        ...state.specs[action.name],
                        selectedVectors: (cur_vectors.includes(action.vector) ?
                            cur_vectors.filter((tag) => tag !== action.vector) : [...cur_vectors, action.vector])
                    }
                }
            };
        case 'TOGGLE_FEATURE':
            var cur_features = state.specs[action.name].selectedFeatures;
            return {
                specs: {
                    ...state.specs,
                    [action.name]: {
                        ...state.specs[action.name],
                        selectedFeatures: (cur_features.includes(action.feature) ?
                            cur_features.filter((feature) => feature !== action.feature) : [...cur_features, action.feature])
                    }
                }
            };
        case 'SET_VALUE_SPEC':
            return {
                specs: {
                    ...state.specs,
                    [action.specName] : {
                        ...state.specs[action.specName],
                        selectedValues: {
                            ...state.specs[action.specName].selectedValues,
                            specification:{
                                ...state.specs[action.specName].selectedValues.specification,
                                [action.groupName]: {
                                    ...state.specs[action.specName].selectedValues.specification[action.groupName],
                                    [action.attributeName]: (action.value)
                                }
                            }
                        }
                    }
                }
            };
        case 'SET_VALUE_FEATURE':
            return {
                specs: {
                    ...state.specs,
                    [action.specName] : {
                        ...state.specs[action.specName],
                        selectedValues: {
                            ...state.specs[action.specName].selectedValues,
                            features:{
                                ...state.specs[action.specName].selectedValues.features,
                                [action.featureName] : {
                                    ...state.specs[action.specName].selectedValues.features[action.featureName],
                                    [action.groupName]: {
                                        ...state.specs[action.specName].selectedValues.features[action.featureName][action.groupName],
                                        [action.attributeName]: (action.value)
                                    }
                                }
                            }
                        }
                    }
                }
            };

        case 'CLEAR_SELECTED_SPECS':
            return initialState;
        default:
            return state
    }
};

export default testvalues;

