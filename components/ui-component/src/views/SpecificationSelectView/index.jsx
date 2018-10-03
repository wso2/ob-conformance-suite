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
import {
    ListGroup, ListGroupItem, Button, FormControl,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
    setTestName,
    toggleSpecification,
    clearTestValues,
    clearSelectedSpecifications,
} from '../../actions';
import AppHeader from '../../partials/AppHeader';

/**
 * ClassName: SpecificationSelectView
 *
 * Responsible for displaying APIs that can be tested through the system
 *
 */
class SpecificationSelectView extends React.Component {
    /**
     *
     * @param {*} props - Class props
     */
    constructor(props) {
        super(props);

        this.isSpecSelected = this.isSpecSelected.bind(this);
        this.toggleSpec = this.toggleSpec.bind(this);
        this.isEmptySelection = this.isEmptySelection.bind(this);
    }

    /**
     *Function to check whether the spec is selected
     * @param {string} name - name
     */
    isSpecSelected(name) {
        const { specifications } = this.props;
        return (specifications.selected.includes(name));
    }

    /**
     *Function to toggle the specification
     * @param {string} specification - specification
     */
    toggleSpec(specification) {
        const { dispatch } = this.props;
        dispatch(toggleSpecification(specification.name));
    }

    /**
     *Function to check whether none of the APis are selected
     */
    isEmptySelection() {
        const { specifications } = this.props;
        const { testvalues } = this.props;
        return specifications.selected.length === 0 || testvalues.name.length === 0;
    }

    dismiss() {
        const { history } = this.props;
        const { dispatch } = this.props;
        history.push('/dashboard');
        dispatch(clearTestValues());
        dispatch(clearSelectedSpecifications());
    }

    /**
     *
     * @returns {string} - HTML markup for list the available APis
     */
    renderSpec(specification) {
        return (
            <ListGroupItem
                key={specification.name}
                onClick={() => { this.toggleSpec(specification); }}
                active={this.isSpecSelected(specification.name)}
            >
                <div className='pull-right checkbox'>
                    <i className={'fas fa-2x fa-' + (this.isSpecSelected(specification.name)
                        ? 'check-square' : 'square fa-square-config')}
                    />
                </div>
                <h4>
                    {specification.title}
                    {' '}
                    {specification.version}
                </h4>
                <p>{specification.description}</p>
            </ListGroupItem>);
    }

    /**
     *
     * @returns {string} - HTML markup for the API selection view
     */
    renderMain(props) {
        return (
            <div className='test-configuration-view'>
                <ul className='nav nav-wizard nav-justified nav-margin'>
                    <li role='presentation' className='active'>
                        <Link to='/tests/new'>
                            <span className='step-number'>01</span>
                            <span className='step-desc'>
                                <h2>Create new Test Plan</h2>
                                <p>Create new test plan for conformance testing</p>
                            </span>
                        </Link>
                    </li>
                    <li role='presentation'>
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
                <div className='input-group'>
                    <span className='input-group-addon span-custom'>Name of the Test Configuration</span>
                    <FormControl
                        type='text'
                        value={props.testvalues.name}
                        onChange={(e) => { props.dispatch(setTestName(e.target.value)); }}
                    />
                </div>
                <br />
                <ListGroup>
                    <ListGroupItem disabled><h4>APIs to be tested</h4></ListGroupItem>
                    {Object.values(props.specifications.specs).map((spec) => {
                        return this.renderSpec(spec);
                    })}
                </ListGroup>
                <p
                    className='text-warning'
                    hidden={!this.isEmptySelection()}
                >
                    <small>* Select at least one API to continue</small>
                </p>
                <Link to='/tests/new/configure'>
                    <Button bsStyle='primary' bsSize='lg' disabled={this.isEmptySelection()}>
                        Configure
                        <i className='fas fa-chevron-right' />
                    </Button>
                </Link>

                <Button
                    className='test-save-btn'
                    bsStyle='default'
                    bsSize='lg'
                    onClick={() => { this.dismiss(); }}
                >
                    Cancel
                </Button>
            </div>
        );
    }

    /**
     *
     * @returns {string} - HTML markup for the SpecificationSelectView
     */
    render() {
        return (
            <div>
                <AppHeader />
                {/* <AppBreadcrumbs/> */}
                <div className='container'>
                    {this.renderMain(this.props)}
                </div>
            </div>
        );
    }
}

SpecificationSelectView.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
    specifications: PropTypes.shape({
        selected: PropTypes.array.isRequired,
        specs: PropTypes.object.isRequired,
    }).isRequired,
    testvalues: PropTypes.shape({
        name: PropTypes.string.isRequired,
    }).isRequired,
    dispatch: PropTypes.func.isRequired,
};

export default connect(state => (
    { specifications: state.specifications, testvalues: state.testvalues }
))(SpecificationSelectView);
