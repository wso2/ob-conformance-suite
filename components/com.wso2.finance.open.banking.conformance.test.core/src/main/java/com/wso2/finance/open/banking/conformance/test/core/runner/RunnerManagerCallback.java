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

package com.wso2.finance.open.banking.conformance.test.core.runner;

import com.wso2.finance.open.banking.conformance.mgt.models.Report;

/**
 * CallBack Interface used to Receive updates from TestRunner Instance.
 */
public interface RunnerManagerCallback {

    /**
     * Processed Feature will be passed to the UI through this callback.
     * @param report
     * @return
     */
    Report onAddResult(Report report);

    /**
     * Overall Report update will be reflected on the UI through this callback.
     * @param report
     */
    void onUpdateResult(Report report);
}
