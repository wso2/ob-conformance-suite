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
    Row, Col, Button,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import AppHeader from './partials/AppHeader';
import RequestBuilder from './utils/RequestBuilder';
import { updateReport } from './actions';
import TestPlanView from './components/TestPlanView';

const client = new RequestBuilder();

/**
 * ClassName: TestHistoryView
 *
 * Responsible for displaying the structure of
 * test history view
 *
 */
class TestHistoryView extends React.Component {
    /**
     *
     * @param {*} props - Class props
     */
    constructor(props) {
        super(props);
        this.runTest = this.runTest.bind(this);
    }

    /**
     *Function to run the test plan
     * @param {object} testPlan - testPlan
     */
    runTest(testPlan) {
        const { dispatch, history } = this.props;
        client.runTestPlan(testPlan).then((response) => {
            dispatch(updateReport(response.data));
            history.push('/tests/report/' + response.data.testId + '/' + response.data.reportId);
        });
    }

    /**
     *
     * @returns {string} - HTML markup for the TestHistoryView
     */
    render() {
        const { testplans, specifications } = this.props;
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
                        {Object.values(testplans).map(
                            plan => (
                                <TestPlanView
                                    plan={plan}
                                    key={plan.testId}
                                    specifications={specifications}
                                    runTest={this.runTest}
                                />),
                        )}
                    </div>
                    <div className='testplan-wrapper' hidden={Object.values(testplans).length !== 0}>
                        <div className='well text-center text-muted'>
                            <h3>No Test Plans Added Yet</h3>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

TestHistoryView.propTypes = {
    testplans: PropTypes.shape().isRequired,
    dispatch: PropTypes.func.isRequired,
    history: PropTypes.shape().isRequired,
    specifications: PropTypes.shape().isRequired,
};

export default withRouter(connect(state => ({
    specifications: state.specifications.specs, testplans: state.testplans.testplans,
}))(TestHistoryView));
