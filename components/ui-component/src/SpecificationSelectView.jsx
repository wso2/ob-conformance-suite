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
import {ListGroup, ListGroupItem, Button} from 'react-bootstrap';
import {connect} from 'react-redux'
import {addSpecification, toggleSpecification, clearSpecifications} from "./actions";
import {Link} from 'react-router-dom'

class SpecificationSelectView extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <AppHeader/>
                <AppBreadcrumbs/>
                <div className={"container"}>
                    {this.renderMain()}
                </div>
            </div>
        )
    }

    renderSpec(specification) {
        return (
            <ListGroupItem key={specification.name} onClick={() => {this.toggleSpec(specification)}} active={this.isSpecSelected(specification.name)}>
                <div className="pull-right checkbox">
                    <i className={"fas fa-2x fa-" + (this.isSpecSelected(specification.name) ? "check-square" : "square fa-square-config")}></i>
                </div>
                <h4>{specification.title} {specification.version}</h4>
                <p>{specification.description}</p>
            </ListGroupItem>);
    }

    isSpecSelected(name){
        return (this.props.specifications.selected.includes(name));
    }

    toggleSpec(specification) {
        this.props.dispatch(toggleSpecification(specification.name))
    }

    isEmptySelection() {
        return this.props.specifications.selected.length === 0;
    }

    renderMain() {
        return (
            <div className={"test-configuration-view"}>
                <h1 className={"page-header"}>Create new test</h1>
                <br/>
                <Panel>
                    <Panel.Heading>Test Configuration</Panel.Heading>
                    <Panel.Body>
                        <div className="input-group">
                            <span className="input-group-addon span-custom">Name of the Test Configuration</span>
                            <input id="msg" type="text" className="form-control" name="msg"
                                   placeholder="Enter Name for the Text Configuration"></input>
                        </div>
                    </Panel.Body>
                </Panel>
                <br/>
                <ListGroup>
                    <ListGroupItem disabled><h4>APIs to be tested</h4></ListGroupItem>
                    {Object.values(this.props.specifications.specs).map((spec) => {
                        return this.renderSpec(spec)
                    })}
                </ListGroup>
                <p className={"text-warning"} hidden={!this.isEmptySelection()}><small>* Select at least one API to continue</small></p>
                <Link to={"/tests/new/configure"}>
                    <Button bsStyle={"secondary"} bsSize={"lg"} disabled={this.isEmptySelection()}>Configure</Button>
                </Link>
            </div>
        );
    }
}

export default connect((state) =>
    ({specifications: state.specifications}))
(SpecificationSelectView);
