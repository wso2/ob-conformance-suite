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
import {updateReport} from "./actions";
const client = new RequestBuilder();

const TestPlanRow = ({report}) => (
    <tr align="left">
        <td>{report.executed}</td>
        <td>{report.state}</td>
        <td className={"overall-results-block"}>
            <p><span style={{color: "green"}}><i className="fas fa-check-circle"/> Passed : 6</span></p>
            <p><span style={{color: "red"}}><i className="fas fa-times-circle"/> Failed : 3</span></p>
            <p><span>Success Rate: 66.67%</span></p>
        </td>
        <td><Link to={"/tests/report/"+report.testId+"/"+report.reportId}>Check Report</Link></td>
    </tr>
);

const TestPlanItem = connect((state) => ({specifications: state.specifications.specs}))(({testPlan,specifications, runTest}) => (

    <Panel defaultExpanded={false}>
        <Panel.Heading>
        <Panel.Title>
            <Row className={"history-view-row"}>
                <Col xs={6}>
                    <p>
                        {testPlan.testPlan.name}
                        <small>
                            <p className={"text-muted"}>
                                <span className={"history-view-inline-specs"}>
                                {Object.keys(testPlan.testPlan.specifications).map((key) => <span>{specifications[key].title} {specifications[key].version}</span>)}
                                </span>
                            </p>
                        </small>
                    </p>
                </Col>
                <Col xs={4}>
                    <ButtonToolbar className="pull-right">
                        <Row>
                            <Col xs={4}><Button onClick={()=>{runTest(testPlan)}} className="round-btn"><i className={"fas fa-play"}/></Button></Col>
                            <Col xs={4}><Button className="round-btn"><i className={"fas fa-cog"}/></Button></Col>
                            <Col xs={4}><Button className="round-btn"><i className={"fas fa-trash"}/></Button></Col>
                        </Row>
                    </ButtonToolbar>
                </Col>
                <Col xs={2}>
                    <Panel.Toggle className="pull-right">
                        <Button className="round-btn"><i className="fas fa-angle-down"></i></Button>
                        
                    </Panel.Toggle>
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
                            <th className={"tableHead"}>Test Run Date</th>
                            <th className={"tableHead"}>Status</th>
                            <th className={"tableHead"}>Summary</th>
                            <th className={"tableHead"}></th>
                        </tr>
                    </thead>
                    <tbody className={"text-center"}>
                        {Object.values(testPlan.reports).map((report) => <TestPlanRow report={report}/>)}
                    </tbody>
                </Table>
            </Panel.Body>
        </Panel.Collapse>
    </Panel>
));

class TestHistoryView extends React.Component{

    constructor(props){
        super(props);
        this.runTest = this.runTest.bind(this);
    }

    runTest(testPlan){
        client.runTestPlan(testPlan).then((response) => {
            this.props.dispatch(updateReport(response.data));
            this.props.history.push("/tests/report/"+response.data.testId+"/"+response.data.reportId);
        })
    }

    render(){
        return(
            <div>
                <AppHeader/>
                {/* <AppBreadcrumbs/> */}
                <div className={"divStyle"}>
                    <div className={"subHeadStyle"}>
                        <Row className="show-grid">
                            <Col xs={8}><h4>Test Plans</h4></Col>
                            <Col xs={4}>
                                <Link to={"/tests/new"}>
                                    <Button className="pull-right btn-secondary" bsStyle="default">
                                        <i className="fas fa-plus"></i>  Test plan
                                    </Button>
                                </Link>
                            </Col>
                        </Row>
                    </div>

                    {Object.values(this.props.testplans).map((plan) =><TestPlanItem testPlan={plan} runTest={this.runTest}/>)}
                </div>
            </div>
        );
    }
}


export default withRouter(connect((state) => ({
    testplans : state.testplans.testplans
}))(TestHistoryView));
