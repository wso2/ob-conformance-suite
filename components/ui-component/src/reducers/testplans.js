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
    testplans: {}
};

const reportsToObj = (reports) =>
    reports.reduce(function(map, report) {
        map[report.reportId] = report;
        return map;
    }, {});

const testplans = (state = initialState, action) => {
    switch (action.type) {
        case 'ADD_TESTPLAN':
            return {
                testplans: {...state.testplans, [action.id] : {
                        testId : action.id,
                        testPlan : action.testplan,
                        reports : reportsToObj(action.reports)
                    }
                },
            };
        case 'UPDATE_REPORT':
            return {
                testplans: {...state.testplans, [action.report.testId] : {
                        ...state.testplans[action.report.testId],
                        reports : {
                            ...state.testplans[action.report.testId].reports,
                            [action.report.reportId] : action.report
                        }
                    }
                },
            };
        case 'CLEAR_TESTPLANS':
            return initialState;
        default:
            return state
    }
};

export default testplans;

