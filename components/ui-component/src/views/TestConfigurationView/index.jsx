/* eslint-disable react/destructuring-assignment */
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
import { withRouter, Link } from 'react-router-dom';
import {
    Grid, Row, Col, Button, ListGroup, Panel,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import axios from 'axios';
import { bootstrapUtils } from 'react-bootstrap/lib/utils';
import AppHeader from '../../partials/AppHeader';
import RequestBuilder from '../../utils/RequestBuilder';
import {
    updateSpecification, addSpecificationToTestValues,
    addTestPlan, clearTestValues, clearSelectedSpecifications, updateReport,
} from '../../actions';
import TestPlanReduxHelper from '../../utils/TestPlanReduxHelper';
import SpecificationEditorView from './SpecificationEditorView';
import SpecificationListGroupItem from './SpecificationListGroupItem';

bootstrapUtils.addStyle(Button, 'secondary');

const client = new RequestBuilder();

/**
 * View for configuration of selected specifications.
 */
class TestConfigurationView extends React.Component {
    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            selectedSpec: null,
        };
        this.selectSpec = this.selectSpec.bind(this);
        this.isCompleted = this.isCompleted.bind(this);
        this.buildTestPlan = this.buildTestPlan.bind(this);
    }

    /**
     * @inheritdoc
     */
    componentDidMount() {
        const { specifications } = this.props;
        const { dispatch } = this.props;
        const { history } = this.props;
        if (specifications.selected.length !== 0) {
            axios.all(specifications.selected.map(key => client.getSingleSpecification(key))).then(
                axios.spread((...specs) => {
                    specs.forEach((spec) => {
                        dispatch(updateSpecification(spec.data.name, spec.data));
                        dispatch(addSpecificationToTestValues(spec.data));
                    });
                }),
            ).finally(() => {
                this.setState({
                    loading: false,
                    selectedSpec: specifications.selected[0],
                });
            });
        } else {
            history.push('/tests/new');
        }
    }

    /**
     * Select Specification for configuration by key.
     * @param {string} key specification name
     */
    selectSpec(key) {
        this.setState({
            selectedSpec: key,
        });
    }

    /**
     * Check if TestPlan configuration fields are filled.
     */
    isCompleted() {
        // return TestPlanReduxHelper.isTestPlanFilled(this.props.testvalues);
    }

    /**
     * Build TestPlan from redux store.
     * @param {boolean} runNow flag to enable imediate execution
     */
    buildTestPlan(runNow) {
        const { testvalues } = this.props;
        const testPlan = TestPlanReduxHelper.buildTestPlanFromTestValues(testvalues);
        const { dispatch } = this.props;
        const { history } = this.props;
        client.postTestPlan({
            testPlan,
            runNow,
        }).then((response) => {
            const reports = runNow ? [response.data.report] : [];
            dispatch(addTestPlan(response.data.testId, testPlan, reports));
            if (runNow) {
                history.push({
                    pathname: '/tests/report/' + response.data.testId + '/' + response.data.report.reportId,
                    state: { fromDashboard: false },
                });
                dispatch(updateReport(response.data.report));
            } else {
                history.push('/dashboard');
            }
        }).finally(() => {
            dispatch(clearTestValues());
            dispatch(clearSelectedSpecifications());
        });
    }

    /**
     * Disimiss configuration and clear reduix store.
     */
    dismiss() {
        const { history } = this.props;
        history.push('/dashboard');
        const { dispatch } = this.props;
        dispatch(clearTestValues());
        dispatch(clearSelectedSpecifications());
    }

    /**
     * Render ListGroupItems fro selected specifications.
     * @returns {Array} Specification ListGroupItems
     */
    renderSpecs() {
        const { specifications } = this.props;
        const { selectedSpec } = this.state;
        return TestPlanReduxHelper.getSelectedSpecsFromState(specifications, specifications.selected)
            .map((spec) => {
                return (
                    <SpecificationListGroupItem
                        selected={spec.name === selectedSpec}
                        key={spec.name}
                        spec={spec}
                        selectElement={this.selectSpec}
                    />
                );
            });
    }

    /**
     * Render Specification Editor.
     * @returns { SpecificationEditorView } editor view
     */
    renderEditor() {
        const { specifications } = this.props;
        const { selectedSpec } = this.state;
        return (
            <SpecificationEditorView
                spec={TestPlanReduxHelper.getSpecFromState(specifications, selectedSpec)}
            />
        );
    }

    /**
     * Render Specification selector and editor.
     * @returns { React.Component } Selector and Editor view
     */
    renderMain() {
        const { selectedSpec } = this.state;
        return (
            <div>
                <br />
                <Grid>
                    <Row>
                        <ul className='nav nav-wizard nav-justified nav-margin'>
                            <li role='presentation'>
                                <Link to='/tests/new'>
                                    <span className='step-number'>01</span>
                                    <span className='step-desc'>
                                        <h2>Create new Test Plan</h2>
                                        <p>Create new test plan for conformance testing</p>
                                    </span>
                                </Link>
                            </li>
                            <li role='presentation' className='active'>
                                <a href='#'>
                                    <span className='step-number'>02</span>
                                    <span className='step-desc'>
                                        <h2>Configure TestPlan</h2>
                                        <p>Configure test details</p>
                                    </span>
                                </a>
                            </li>
                        </ul>
                        <br />
                        <br />
                    </Row>
                    <Row>
                        <Col md={4} className='navigation-list'>
                            <Panel>
                                <Panel.Heading>Selected API Specifications</Panel.Heading>
                                <ListGroup>
                                    {this.renderSpecs()}
                                </ListGroup>
                            </Panel>
                        </Col>
                        <Col md={8}>
                            {selectedSpec ? this.renderEditor() : null}
                            <br />
                            <div>
                                <Button
                                    bsStyle='primary'
                                    bsSize='lg'
                                    disabled={this.isCompleted()}
                                    onClick={() => { this.buildTestPlan(true); }}
                                >
                                    Save and Run
                                </Button>
                                <Button
                                    className='test-save-btn'
                                    bsStyle='secondary'
                                    bsSize='lg'
                                    disabled={this.isCompleted()}
                                    onClick={() => { this.buildTestPlan(false); }}
                                >
                                    Save
                                </Button>
                                <Button
                                    className='test-save-btn'
                                    bsStyle='default'
                                    bsSize='lg'
                                    onClick={() => { this.dismiss(); }}
                                >
                                    Cancel
                                </Button>
                            </div>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }

    /**
     * @inheritdoc
     */
    render() {
        return (
            <div>
                <AppHeader />
                <div className='container'>
                    {this.state.loading ? <h1>Loading</h1> : this.renderMain()}
                </div>
            </div>
        );
    }
}

TestConfigurationView.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
    specifications: PropTypes.shape({
        selected: PropTypes.array.isRequired,
    }).isRequired,
    testvalues: PropTypes.shape(
    ).isRequired,
    dispatch: PropTypes.func.isRequired,
};

export default withRouter(connect(state => ({
    specifications: state.specifications,
    testvalues: state.testvalues,
}))(TestConfigurationView));
