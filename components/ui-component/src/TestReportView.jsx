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
import AppHeader from "./partials/AppHeader";
import {ListGroup, ListGroupItem, Glyphicon, Button, Grid, Row, Col, Panel, Well} from 'react-bootstrap';
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import '../public/css/report-style.css'
import {connect} from 'react-redux'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheckCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons'
import { PieChart, Pie } from 'recharts';
import RequestBuilder from './utils/RequestBuilder';
import TestReportHelper from './utils/TestReportHelper';

const client = new RequestBuilder();
const reportHelper = new TestReportHelper();

const ReportSpec = connect((state) => ({specifications: state.specifications,}))(({spec,specName,specifications}) => (
  <div>
    <h3>{specifications.specs[specName].title}</h3>
    {spec.map(featurex => <ReportFeature feature={featurex}/>)}
  </div>
));

const ReportFeature = ({feature}) => (
    <Panel bsStyle="primary">
        <Panel.Heading>
            <Panel.Title componentClass="h3">{feature.name}</Panel.Title>
        </Panel.Heading>
        <Panel.Body>
          <ListGroup>
            {feature.elements.map(elementx => <FeatureElement element={elementx}/>)}
          </ListGroup>
        </Panel.Body>
    </Panel>
);

const stepStatus = (steps) => {
  var status = true;
  var error="";
  

  steps.forEach(step => {
    status = status && (step.result.status === "passed");
    error = step.result.error_message;
  });
  return status? <p className="passed"><FontAwesomeIcon icon={faCheckCircle}/>Passed</p> : <div><p className="failed"><FontAwesomeIcon icon={faTimesCircle}/>Failed</p> <p className="error">{error}</p></div>;
} 
const FeatureElement = ({element}) => (
    <ListGroupItem>
        <h4>{element.name}</h4>
        {stepStatus(element.steps)}
    </ListGroupItem>
)

const ElementStep = ({step}) => (
  step.result.status
)


class TestReportView extends React.Component {

    constructor(props){
            super(props);
            this.state = {
                uuid: props.match.params.uuid,
                loading: true,
                data: null,
                currentSpecName: "specExample"
            }

            this.interval = null;
            this.renderMain = this.renderMain.bind(this);
            this.appendResults = this.appendResults.bind(this);
    }


    componentDidMount() {
        var currentRoute = this.props.location.pathname
        //console.log(currentLocation);
        client.getResultsForTestPlan(this.state.uuid).then((response)=>{
            this.setState({
                loading:false,
                data: response.data
            })
        });

        if(currentRoute.includes("running")){
            console.log(this.state.data);
            this.interval = setInterval(() => this.appendResults(), 2000);
        }
    }
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    appendResults(){
        client.pollResultsForTestPlan(this.state.uuid).then((response)=>{
            var resultObject = this.state.data;
            response.data.forEach((feature) => {
                console.log(feature.featureResult);
                resultObject = {
                    ...resultObject,
                    [feature.specName] : [...resultObject[feature.specName],feature.featureResult]
                };
            });
            //console.log("F",resultObject);
            this.setState({
                data : resultObject
            })
        });
    }

  render() {
    return (
        <div>
            <AppHeader/>
            <AppBreadcrumbs/> 
            <br/>
            {this.state.loading ? <h1>Loading..</h1> : this.renderMain()}
        </div>
    );
  }

  renderMain(){
    return (
        <Grid>
    <Row>
        <Col md={3}>
            <Well bsSize="small">
                <h2>Test Summary</h2>
                <p>Passed : </p>
                <p>Failed : </p>
                <p>Success Rate :</p>
            </Well>
        </Col>

        <Col md={9}>
            <Well bsSize="small">
                <h2>Detailed Report</h2>
                {Object.keys(this.state.data).map((key) => <ReportSpec spec={this.state.data[key]} specName={key}/>)}
            </Well>
        </Col>
    </Row>
</Grid>
    );
  }
}

export default TestReportView;
