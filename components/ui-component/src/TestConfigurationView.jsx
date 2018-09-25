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
import RequestBuilder from './utils/RequestBuilder';
import {withRouter, Link} from 'react-router-dom'
import { Grid, Row, Col, Button, ListGroup, Panel} from 'react-bootstrap';
import {updateSpecification,addSpecificationToTestValues,
    addTestPlan,clearTestValues,clearSelectedSpecifications,updateReport} from "./actions";
import {connect} from 'react-redux'
import TestPlanReduxHelper from './utils/TestPlanReduxHelper'
import axios from 'axios';
import {Specification, SpecificationEditor} from "./components/TestPlanComponents";



const client = new RequestBuilder();

class TestConfigurationView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            selectedSpec: null
        };
        this.selectSpec = this.selectSpec.bind(this);
        this.isCompleted = this.isCompleted.bind(this);
        this.buildTestPlan = this.buildTestPlan.bind(this);
        this.saveTestPlan = this.saveTestPlan.bind(this);
    }

    componentDidMount() {
        if (this.props.specifications.selected.length !== 0) {
            axios.all(this.props.specifications.selected.map(key => client.getSingleSpecification(key))).then(
                axios.spread((...specs) => {
                    specs.forEach((spec) => {
                        this.props.dispatch(updateSpecification(spec.data.name,spec.data));
                        this.props.dispatch(addSpecificationToTestValues(spec.data));
                    });
                })).finally(() => {
                this.setState({
                    loading: false,
                    selectedSpec: this.props.specifications.selected[0]
                });
            });
        } else {
            this.props.history.push("/tests/new");
        }
    }

    renderEditor() {
        return <SpecificationEditor
            spec={TestPlanReduxHelper.getSpecFromState(this.props.specifications, this.state.selectedSpec)}/>;
    }

    renderSpecs() {
        return TestPlanReduxHelper.getSelectedSpecsFromState(this.props.specifications,this.props.specifications.selected)
            .map(spec => {
                return (
                    <Specification selected={spec.name == this.state.selectedSpec} key={spec.name} spec={spec} selectElement={this.selectSpec}/>
                );
        });
    }

    selectSpec(key) {
        this.setState({
            selectedSpec: key
        })
    }

    isCompleted() {
        //return TestPlanReduxHelper.isTestPlanFilled(this.props.testvalues);
    }

    buildTestPlan(runNow){
        let testPlan = TestPlanReduxHelper.buildTestPlanFromTestValues(this.props.testvalues);
        client.postTestPlan({
            testPlan : testPlan,
            runNow : runNow
        }).then((response) => {
            var reports = runNow ? [response.data.report] : [];
            this.props.dispatch(addTestPlan(response.data.testId,testPlan,reports));
            if (runNow){
                this.props.history.push({
                    pathname: "/tests/report/"+response.data.testId+"/"+response.data.report.reportId,
                    state: {fromDashboard: false}
                });
                this.props.dispatch(updateReport(response.data.report));
            }else{
                this.props.history.push("/dashboard");
            }
        }).finally(() => {
            this.props.dispatch(clearTestValues());
            this.props.dispatch(clearSelectedSpecifications());
        });
    }

    dismiss() {
        this.props.history.push("/dashboard");
        this.props.dispatch(clearTestValues());
        this.props.dispatch(clearSelectedSpecifications());
    }

    saveTestPlan(){
        let testConfiguration = TestPlanReduxHelper.buildTestPlanFromTestValues(this.props.testvalues);
        console.log(testConfiguration)
    }

    renderMain() {
        return (
            <div>
                <br/>
                <Grid>
                    <Row>
                        <ul className="nav nav-wizard nav-justified nav-margin">
                            <li role="presentation">
                                <Link to={"/tests/new"}>
                                    <span className="step-number">01</span>
                                    <span className="step-desc">
                                <h2>Create new Test Plan</h2>
                                <p>Create new test plan for conformance testing</p>
                            </span>
                                </Link>
                            </li>
                            <li role="presentation" className="active">
                                <a href="#">
                                    <span className="step-number">02</span>
                                    <span className="step-desc">
                                <h2>Configure TestPlan</h2>
                                <p>Configure test details</p>
                            </span>
                                </a>
                            </li>
                        </ul>
                        <br/>
                        <br/>
                    </Row>
                    <Row>
                        <Col md={4} className={"navigation-list"}>
                            <Panel>
                                <Panel.Heading>Selected API Specifications</Panel.Heading>
                                <ListGroup>
                                    {this.renderSpecs()}
                                </ListGroup>
                            </Panel>
                        </Col>
                        <Col md={8}>
                            {this.state.selectedSpec ? this.renderEditor() : null}
                            <br/>
                            <div>
                                <Button bsStyle={"primary"} bsSize={"lg"}
                                        disabled={this.isCompleted()}
                                        onClick={()=>{this.buildTestPlan(true)}}
                                >Save and Run</Button>
                                <Button className="test-save-btn" bsStyle={"secondary"} bsSize={"lg"}
                                        disabled={this.isCompleted()}
                                        onClick={()=>{this.buildTestPlan(false)}}
                                >Save</Button>
                                <Button className="test-save-btn" bsStyle={"default"} bsSize={"lg"}
                                        onClick={()=>{this.dismiss()}}
                                >Cancel</Button>
                            </div>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }

    render() {
        return (
            <div>
                <AppHeader/>
                {/* <AppBreadcrumbs/> */}
                <div className={"container"}>
                    {this.state.loading ? <h1>Loading</h1> : this.renderMain()}
                </div>
            </div>
        );
    }
}

export default withRouter(connect((state) => ({
    specifications: state.specifications,
    testvalues: state.testvalues
}))(TestConfigurationView));
