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

import axios from 'axios';

const instance = axios.create();

export default class RequestBuilder {

    constructor() {
        this.base_url = 'http://localhost:9090/';
        instance.defaults.headers.common['Authorization'] = 'Basic ' + btoa("admin:admin");
    }

    getSpecifications() {
        return instance.get(this.base_url + 'specifications/');
    }

    getSingleSpecification(name) {
        return instance.get(this.base_url + 'specifications/' + name);
    }

    postTestPlan(testplan) {
        return instance.post(this.base_url + 'testplan/', testplan);
    }

    runTestPlan(testplan) {
        return instance.get(this.base_url + 'testplan/' + testplan.testId + '/run/');
    }

    pollResultsForTestPlan(id) {
        return instance.get(this.base_url + 'runner/' + id + '/poll/');
    }

    getResultsForTestPlan(uuid, id) {
        return instance.get(this.base_url + 'results/' + uuid + '/' + id);
    }

    getTestPlans() {
        return instance.get(this.base_url + 'testplan/');
    }
}
