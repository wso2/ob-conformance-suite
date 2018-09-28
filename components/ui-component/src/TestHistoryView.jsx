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
import { connect } from 'react-redux';
import { withRouter, Link } from 'react-router-dom';
import {
    Table, Row, Col, Button, Panel, ButtonToolbar,
} from 'react-bootstrap';
import AppHeader from './partials/AppHeader';
import RequestBuilder from './utils/RequestBuilder';
import TestReportHelper from './utils/TestReportHelper';
import '../public/css/report-style.scss';
import { updateReport } from './actions';

const client = new RequestBuilder();
const reportHelper = new TestReportHelper();

/*
 * Row of the test plan table
 */
const TestPlanRow = ({ report }) => (
    <tr align='left'>
        <td>{report.executed}</td>
        <td>{report.state}</td>
        <td className='overall-results-block'>
            <p>
                <span style={{ color: 'green' }}>
                    <i className='fas fa-check-circle' />
                    {' '}
                    Passed :
                    {reportHelper.getTestSummary(report.result).passed}
                </span>
            </p>
            {reportHelper.getTestSummary(report.result).failed > 0 ? (
                <p>
                    <span style={{ color: 'red' }}>
                        <i className='fas fa-times-circle' />
                        {' '}
                        Failed :
                        {reportHelper.getTestSummary(report.result).failed}
                    </span>
                </p>
            )
                : null
            }
            <p>
                <span>
                    Success Rate:
                    {reportHelper.getTestSummary(report.result).rate}
                    {' '}
%
                </span>
            </p>
        </td>
        <td>
            <Link to={
                {
                    pathname: '/tests/report/' + report.testId + '/' + report.reportId,
                    state: { fromDashboard: true },
                }
            }
            >
            Check Report
            </Link>
        </td>
    </tr>
);

/*
 * Test plan panel
 */
class TestPlanView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
        };
    }

    /*
     *Funtion fires when panel toggles
     */
    togglePlan() {
        console.log('Toggled');
    }

    render() {
        return (
            <Panel defaultExpanded={false} expanded={this.state.open} onToggle={() => this.togglePlan()}>
                <Panel.Heading>
                    <Panel.Title>
                        <Row className='history-view-row'>
                            <Col xs={6}>
                                <div>
                                    {this.props.plan.testPlan.name}
                                    <small>
                                        <p className='text-muted'>
                                            <span className='history-view-inline-specs'>
                                                {Object.keys(this.props.plan.testPlan.specifications).map(key => (
                                                    <span key={this.props.specifications[key].title}>
                                                        {this.props.specifications[key].title}
                                                        {this.props.specifications[key].version}
                                                    </span>
                                                ))}
                                            </span>
                                        </p>
                                    </small>
                                </div>
                            </Col>
                            <Col xs={5}>
                                <ButtonToolbar className='pull-right'>
                                    <Button
                                        onClick={() => { this.props.runTest(this.props.plan); }}
                                        className='round-btn'
                                    >
                                        <i className='fas fa-lg fa-play' />
                                    </Button>
                                    <Button className='round-btn'>
                                        <i className='fas fa-lg fa-cog' />
                                    </Button>
                                    <Button className='round-btn'>
                                        <i className='fas fa-lg fa-trash' />
                                    </Button>
                                </ButtonToolbar>
                            </Col>
                            <Col xs={1}>
                                <Button className='round-btn' onClick={() => this.setState({ open: !this.state.open })}>
                                    <i className={'fas fa-lg fa-' + (this.state.open ? 'angle-up' : 'angle-down')} />
                                </Button>
                            </Col>
                        </Row>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Collapse>
                    <Panel.Body collapsible>
                        <b>Test Iterations</b>
                        <Table className='test-history-table' striped bordered condensed hover>
                            <thead>
                                <tr>
                                    <th className='tableHead'>Test Run Date</th>
                                    <th className='tableHead'>Status</th>
                                    <th className='tableHead'>Summary</th>
                                    <th className='tableHead' />
                                </tr>
                            </thead>
                            <tbody className='text-center'>
                                {Object.values(this.props.plan.reports).map(report => <TestPlanRow key={report.reportId} report={report} />)}
                            </tbody>
                        </Table>
                    </Panel.Body>
                </Panel.Collapse>
            </Panel>
        );
    }
}

class TestHistoryView extends React.Component {
    constructor(props) {
        super(props);
        this.runTest = this.runTest.bind(this);
    }

    /*
     *Function to run the test plan
     */
    runTest(testPlan) {
        client.runTestPlan(testPlan).then((response) => {
            this.props.dispatch(updateReport(response.data));
            this.props.history.push('/tests/report/' + response.data.testId + '/' + response.data.reportId);
        });
    }

    render() {
        return (
            <div>
                <AppHeader />
                {/* <AppBreadcrumbs/> */}
                <div className='container'>
                    <div className='subHeadStyle'>
                        <Row className='show-grid'>
                            <Col xs={8}><h3>Test Plans</h3></Col>
                            <Col xs={4}>
                                <div className='pull-right button-header'>
                                    <Link to='/tests/new'>
                                        <Button bsStyle='primary'>
                                            <i className='fas fa-plus' />
                                            {' '}
                                            {' '}
                                            Test plan
                                        </Button>
                                    </Link>
                                </div>
                            </Col>
                        </Row>
                    </div>
                    <div className='testplan-wrapper'>
                        {Object.values(this.props.testplans).map(plan => <TestPlanView plan={plan} key={plan.testId} specifications={this.props.specifications} runTest={this.runTest} />)}
                    </div>
                    <div className='testplan-wrapper' hidden={Object.values(this.props.testplans).length !== 0}>
                        <div className='well text-center text-muted'>
                            <h3>No Test Plans Added Yet</h3>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}


export default withRouter(connect(state => ({
    specifications: state.specifications.specs, testplans: state.testplans.testplans,
}))(TestHistoryView));
