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

import React from 'react'
import {Switch, Route} from 'react-router-dom';
import AppHeader from "./partials/AppHeader";
import SpecificationSelectView from "./SpecificationSelectView";
import TestConfigurationView from "./TestConfigurationView";
import {createStore} from 'redux'
import {Provider} from 'react-redux'
import rootReducer from './reducers';

const store = createStore(rootReducer);

const App = () => (
    <Provider store={store}>
        <Switch>
            <Route exact path='/' component={AppHeader}/>
            <Route exact path='/tests/new' component={SpecificationSelectView}/>
            <Route exact path='/tests/new/configure' component={TestConfigurationView}/>
        </Switch>
    </Provider>
);

export default App;