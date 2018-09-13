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
import {ListGroup, ListGroupItem, FormControl, Button, Well, Row} from 'react-bootstrap';
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
                <div className="pull-right">
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
            <div>
                <h1>Available API Specifications</h1>
                <div class="input-group">
                    <span class="input-group-addon span-custom">Name of the Test Configuration</span>
                    <input id="msg" type="text" class="form-control" name="msg" placeholder="Enter Name for the Text Configuration"></input>
                </div>

                {/* <Panel>
                    <Panel.Heading>Name of the Test Configuration</Panel.Heading>
                    <Panel.Body>
                    <form>
                        <FormControl type="text" value="" placeholder="Enter Name for the Text Configuration"/>
                    </form>
                    </Panel.Body>
                </Panel> */}
                <hr/>
                <ListGroup>
                    <ListGroupItem disabled><h4>Select the APIs to test</h4></ListGroupItem>
                    {Object.values(this.props.specifications.specs).map((spec) => {
                        return this.renderSpec(spec)
                    })}
                </ListGroup>
                <div className={"text-center"}>
                    <Link to={"/tests/new/configure"}>
                        <Button bsStyle={"primary"} bsSize={"lg"} disabled={this.isEmptySelection()}>Configure</Button>
                    </Link>
                </div>
            </div>
        );
    }
}

export default connect((state) =>
    ({specifications: state.specifications}))
(SpecificationSelectView);
