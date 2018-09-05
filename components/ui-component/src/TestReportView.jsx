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

import React from 'react';
import data from './report.json';
import axios from 'axios';
import AppHeader from "./partials/AppHeader";
import {withRouter} from 'react-router-dom';
import {ListGroup, ListGroupItem, Glyphicon, Button, Grid, Row, Col} from 'react-bootstrap';
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import BootstrapTable from 'react-bootstrap-table-next';



const columns = [{
    dataField: 'feature',
    text: 'Feature'
  }, {
    dataField: 'scenario',
    text: 'Test Scenario'
  }, {
    dataField: 'status',
    text: 'Status'
  }];

  const products = [
    {feature: '1', scenario:'1', status: '1'}, 
    {feature: '2', scenario:'2', status: '2'},
    {feature: '3', scenario:'3', status: '3'}
    ];

const ReportFeature = ({feature}) => (
    <div>
        <h1>{feature.name}</h1>
        {feature.elements.map(elementx => <ReportElement element={elementx}/>)}
    </div>
);

const ReportElement = ({element}) => (
    <div>
        <p>{element.name}</p>
    </div>
)

class TestReportView extends React.Component {
  render() {
    return (
        <div>
            <AppHeader/>
            <AppBreadcrumbs/>
            <div className={"container"}>
                    {data.map(featurex => <ReportFeature feature={featurex}/>)}
            </div>
            <div>
              <BootstrapTable keyField='id' data={ products } columns={ columns } />
            </div>
        </div>
    );
  }
}

export default TestReportView;
