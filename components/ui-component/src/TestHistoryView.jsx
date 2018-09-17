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
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import {connect} from 'react-redux'
import {withRouter, Link} from 'react-router-dom'
import {Table, Row, Col, Button, Panel, ButtonGroup, PanelGroup, ButtonToolbar} from 'react-bootstrap';
import RequestBuilder from './utils/RequestBuilder';
import '../public/css/report-style.scss'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheckCircle, faTimesCircle, faPercent} from "@fortawesome/free-solid-svg-icons";
const client = new RequestBuilder();




const TestPlanRow = connect((state) => ({specifications: state.specifications.specs}))(({testPlan,specifications}) => (
    <tr align="left">
        <td>{Object.keys(testPlan.testPlan.specifications).map((key) => <p>{specifications[key].title} {specifications[key].version}</p>)}</td>
        <td>{testPlan.testPlan.lastRun}</td>
        <td><b>{testPlan.status}</b></td>
        <td className={"overall-results-block"}>
            <p><span style={{color: "green"}}><FontAwesomeIcon icon={faCheckCircle}/> Passed : 6</span></p>
            <p><span style={{color: "red"}}><FontAwesomeIcon icon={faTimesCircle}/> Failed : 3</span></p>
            <p><span><FontAwesomeIcon icon={faPercent}/> Success Rate: 66.67%</span></p>
        </td>
        <td><Link to={"/tests/report/"+testPlan.testId}>Check Report</Link></td>
    </tr>
));

const TestPlanItem = connect((state) => ({specifications: state.specifications.specs}))(({testPlan,specifications, id}) => (

    <Panel defaultExpanded={false}>
        <Panel.Heading>
        <Panel.Title>
            <Row>
                <Col xs={6}><Panel.Toggle>Security Test Configuration</Panel.Toggle></Col>
                <Col xs={4}>
                    <ButtonToolbar className="pull-right">
                        <Button bsStyle="secondary" className="round-btn"><i className={"fas fa-play"}/></Button>
                        <Button bsStyle="secondary" className="round-btn"><i className={"fas fa-cog"}/></Button>
                        <Button bsStyle="secondary" className="round-btn"><i className={"fas fa-trash"}/></Button>
                    </ButtonToolbar>
                </Col>
                <Col xs={2}>
                    <div className="pull-right"><i className={"fas fa-angle-down"}/></div>
                    {/* <i className={"fas fa-" + expanded ? "angle-up" : "angle-down")}/> */}
                </Col>
                </Row>
        </Panel.Title>
        </Panel.Heading>
        <Panel.Collapse>
            <Panel.Body collapsible>
                <b>Test Iterations</b>
                <Table className = "test-history-table" striped bordered condensed hover>
                    <thead>
                        <tr>
                            <th className={"tableHead"}>Specifications</th>
                            <th className={"tableHead"}>Test Run Date</th>
                            <th className={"tableHead"}>Status</th>
                            <th className={"tableHead"}>Summary</th>
                            <th className={"tableHead"}></th>
                        </tr>
                    </thead>
                    <tbody className={"text-center"}>
                        <tr align="left">
                            <td>{Object.keys(testPlan.testPlan.specifications).map((key) => <p>{specifications[key].title} {specifications[key].version}</p>)}</td>
                            <td>{testPlan.testPlan.lastRun}</td>
                            <td><b>{testPlan.status}</b></td>
                            <td className={"overall-results-block"}>
                                <p><span style={{color: "green"}}><FontAwesomeIcon icon={faCheckCircle}/> Passed : 6</span></p>
                                <p><span style={{color: "red"}}><FontAwesomeIcon icon={faTimesCircle}/> Failed : 3</span></p>
                                <p><span> Success Rate: 66.67%</span></p>
                            </td>
                            <td><Link to={"/tests/report/"+testPlan.testId}>Check Report</Link></td>
                        </tr>
                        <tr align="left">
                            <td>{Object.keys(testPlan.testPlan.specifications).map((key) => <p>{specifications[key].title} {specifications[key].version}</p>)}</td>
                            <td>{testPlan.testPlan.lastRun}</td>
                            <td><b>{testPlan.status}</b></td>
                            <td className={"overall-results-block"}>
                                <p><span style={{color: "green"}}><FontAwesomeIcon icon={faCheckCircle}/> Passed : 6</span></p>
                                <p><span style={{color: "red"}}><FontAwesomeIcon icon={faTimesCircle}/> Failed : 3</span></p>
                                <p><span> Success Rate: 66.67%</span></p>
                            </td>
                            <td><Link to={"/tests/report/"+testPlan.testId}>Check Report</Link></td>
                        </tr>
                    </tbody>
                </Table>
            </Panel.Body>
        </Panel.Collapse>
    </Panel>
));


class TestHistoryView extends React.Component{

    constructor(props){
        super(props);
    }

    render(){
        return(
            <div>
                <AppHeader/>
                <AppBreadcrumbs/>
                <div className={"divStyle"}>
                    <div className={"headStyle"}>National Australia Bank (NAB)</div>
                    <div className={"subHeadStyle"}>
                        <Row className="show-grid">
                            <Col xs={8}><h4>Test History</h4></Col>
                            <Col xs={4}>
                                <Link to={"/tests/new"}>
                                    <Button className="pull-right btn-secondary" bsStyle="default">
                                        + Test Configuration
                                    </Button>
                                </Link>
                            </Col>
                        </Row>
                    </div>
                    <Panel>
                        <Panel.Heading>
                            <Panel.Title>
                                Test Configurations
                            </Panel.Title>
                        </Panel.Heading>
                        <Panel.Body>
                        {Object.values(this.props.testplans).map((plan) =><TestPlanItem testPlan={plan}/>)}
                        </Panel.Body>
                    </Panel>
                    
                    {/* <Table striped bordered condensed hover>
                        <thead>
                            <tr>
                                <th className={"tableHead"}>Specifications</th>
                                <th className={"tableHead"}>Last Test Run Date</th>
                                <th className={"tableHead"}>Test Status</th>
                                <th className={"tableHead"}>Summary</th>
                                <th className={"tableHead"}>Results</th>
                            </tr>
                        </thead>
                        <tbody className={"text-center"}>
                            {Object.values(this.props.testplans).map((plan) => <TestPlanRow testPlan={plan}/> )}
                        </tbody>
                    </Table> */}
                </div>
            </div>
        );
    }
}


export default withRouter(connect((state) => ({
    testplans : state.testplans.testplans
}))(TestHistoryView));
