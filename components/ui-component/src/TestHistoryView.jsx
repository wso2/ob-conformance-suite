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

class TestHistoryView extends React.Component{

    constructor(props){
        super(props);
        this.runTest = this.runTest.bind(this);

        this.state={
            open:false
        }
    }

    runTest(testPlan){
        console.log(testPlan);
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
                <div className={"container"}>
                    <div className={"subHeadStyle"}>
                        <Row className="show-grid">
                            <Col xs={8}><h3>Test Plans</h3></Col>
                            <Col xs={4}>
                                <div className="pull-right button-header">
                                    <Link to={"/tests/new"}>
                                        <Button bsStyle="primary">
                                            <i className="fas fa-plus"></i>  Test plan
                                        </Button>
                                    </Link>
                                </div>
                            </Col>
                        </Row>
                    </div>
                    <div className="testplan-wrapper">
                        {Object.values(this.props.testplans).map((plan) => 
                            <Panel defaultExpanded={false}>
                                <Panel.Heading>
                                    <Panel.Title>
                                        <Row className={"history-view-row"}>
                                            <Col xs={6}>
                                                <p>{plan.testPlan.name}
                                                    <small>
                                                        <p className={"text-muted"}><span className={"history-view-inline-specs"}>
                                                            {Object.keys(plan.testPlan.specifications).map((key) => <span>{this.props.specifications[key].title} {this.props.specifications[key].version}</span>)}
                                                        </span></p>
                                                    </small>
                                                </p>
                                            </Col>
                                            <Col xs={5}>
                                                <ButtonToolbar className="pull-right">
                                                    <Button onClick={()=>{this.runTest(plan)}} className="round-btn"><i className={"fas fa-lg fa-play"}/></Button>
                                                    <Button className="round-btn"><i className={"fas fa-lg fa-cog"}/></Button>
                                                    <Button className="round-btn"><i className={"fas fa-lg fa-trash"}/></Button>
                                                </ButtonToolbar>
                                            </Col>
                                            <Col xs={1}>
                                                <Panel.Toggle className="pull-right" onClick={() => this.setState({ open: !this.state.open })}>
                                                    <Button className="round-btn"><i className={"fas fa-lg fa-" + (this.state.open ? "angle-up": "angle-down")}></i></Button>
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
                                                {Object.values(plan.reports).map((report) => <TestPlanRow report={report}/>)}
                                            </tbody>
                                        </Table>
                                    </Panel.Body>
                                </Panel.Collapse>
                            </Panel>
                        )}
                    </div>
                </div>
            </div>
        );
    }
}


export default withRouter(connect((state) => ({
    specifications: state.specifications.specs, testplans : state.testplans.testplans
}))(TestHistoryView));
