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
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
    setTestName,
    toggleSpecification,
    clearTestValues,
    clearSelectedSpecifications,
} from './actions';
import AppHeader from './partials/AppHeader';

class SpecificationSelectView extends React.Component {
    constructor(props) {
        super(props);
    }

    isSpecSelected(name) {
        return (this.props.specifications.selected.includes(name));
    }

    toggleSpec(specification) {
        this.props.dispatch(toggleSpecification(specification.name));
    }

    isEmptySelection() {
        return this.props.specifications.selected.length === 0 || this.props.testvalues.name.length === 0;
    }

    dismiss() {
        this.props.history.push('/dashboard');
        this.props.dispatch(clearTestValues());
        this.props.dispatch(clearSelectedSpecifications());
    }

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

    renderMain() {
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
                        value={this.props.testvalues.name}
                        onChange={(e) => { this.props.dispatch(setTestName(e.target.value)); }}
                    />
                </div>
                <br />
                <ListGroup>
                    <ListGroupItem disabled><h4>APIs to be tested</h4></ListGroupItem>
                    {Object.values(this.props.specifications.specs).map((spec) => {
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

    render() {
        return (
            <div>
                <AppHeader />
                {/* <AppBreadcrumbs/> */}
                <div className='container'>
                    {this.renderMain()}
                </div>
            </div>
        );
    }
}

export default connect(state => (
    { specifications: state.specifications, testvalues: state.testvalues }
))(SpecificationSelectView);
