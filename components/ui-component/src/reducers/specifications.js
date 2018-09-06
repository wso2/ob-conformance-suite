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
    specs: {},
    selected: []
};


const specifications = (state = initialState, action) => {
    switch (action.type) {
        case 'ADD_SPEC':
            return {
                specs: {...state.specs, [action.name] : action.specification},
                selected: [...state.selected]
            };
        case 'TOGGLE_SPEC':
            return {
                specs: {...state.specs},
                selected: (state.selected.includes(action.name) ? state.selected.filter((key) => key !== action.name) : [...state.selected, action.name])
            };

        case 'CLEAR_SELECTED_SPECS':
            return {
                specs: {...state.specs},
                selected: []
            };

        case 'UPDATE_SPEC':
            state.specs[action.name] = action.specification;
            return {
                ...state
            };
        case 'CLEAR_SPECS':
            return {
                specs: {},
                selected: [...state.selected]
            };
        default:
            return state
    }
};

export default specifications;

