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
import TestPlanReduxHelper from "../utils/TestPlanReduxHelper";
import {connect} from "react-redux";
import {ListGroup, ListGroupItem, Panel, Row, Table } from 'react-bootstrap';
import {toggleVector, toggleFeature} from "../actions";
import AttributeGroup from "./AttributeGroup";

const ScenariodataRow = connect((state) => ({specifications: state.specifications.specs}))(({scenario}) => (
    <tr align="left">
        <td>{scenario.scenarioName}</td>
        <td>{scenario.specName}</td>
        <td>{scenario.specSection}</td>
    </tr>
));

export const Feature = connect((state) => ({testvalues: state.testvalues}))(({feature, specName, dispatch, testvalues}) => (
    <div className="tc-feature-panel">
        <Panel expanded={TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName).includes(feature.uri.path)}>
            <Panel.Heading onClick={() => {dispatch(toggleFeature(specName, feature.uri.path))}}>
                <div className="pull-right tc-checkbox">
                    <i className={"fas fa-" + (TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName)
                        .includes(feature.uri.path) ? "check-square check-square-m" : "square fa-1x fa-square-list")}/>
                </div>
                <Panel.Title>
                    <div className="tc-feature-panel-head">
                        <p>Execute Test {feature.title}</p>
                        <small><p className={"text-muted"}>{feature.description}</p></small>
                    </div>
                </Panel.Title>
            </Panel.Heading>
            <Panel.Collapse>
                <Panel.Body>
                    <Row>
                        <Panel className="scenario-panel" defaultExpanded={false}>
                            <Panel.Title className="scenario-panel">
                                <Panel.Toggle className="scenario-panel-title">Show Scenarios</Panel.Toggle>
                            </Panel.Title>
                            <Panel.Collapse>
                                <Panel.Body className="scenario-panel-body">
                                    <Table striped bordered condensed hover>
                                        <thead>
                                            <tr align="left">
                                                <th className={"tableHead"}>Scenario Name</th>
                                                <th className={"tableHead"}>Spec Name</th>
                                                <th className={"tableHead"}>Section</th>
                                            </tr>
                                        </thead>
                                        <tbody className={"text-center"}>
                                            {feature.scenarios.map((scenario) => 
                                                <ScenariodataRow key={feature.uri.path} scenario={scenario}/>)}
                                        </tbody>
                                    </Table>
                                </Panel.Body>
                            </Panel.Collapse>
                        </Panel>
                    </Row>
                    {feature.attributeGroups ? <hr/> : []}
                    {feature.attributeGroups 
                        ? feature.attributeGroups.map(group => 
                            <AttributeGroup scope={"feature"} specName={specName} featureName={feature.uri.path} 
                                group={group} key={group.groupName}/>) 
                        : []}
                </Panel.Body>
            </Panel.Collapse>
        </Panel>
    </div>
));
    
export const Vector = connect((state) => ({testvalues: state.testvalues}))(({testvalues, specName, vector, dispatch}) => (
    <ListGroupItem onClick={() => {
        dispatch(toggleVector(specName, vector.tag))
    }}>
        <div className="pull-right">
            <i className={"fas fa-" + (TestPlanReduxHelper.getSelectedVectorsFromState(testvalues, specName)
                .includes(vector.tag) ? "check-square check-square-m" : "square fa-1x fa-square-list")}/>
        </div>
        <p>Test {vector.title}</p>
    </ListGroupItem>
));


export const Specification = ({spec,selectElement, selected}) => (
    <ListGroupItem key={"root-spec"} onClick={() => {selectElement(spec.name)}} active={selected}>
        <h4>{spec.title} {spec.version}</h4>
        <p>{spec.description}</p>
    </ListGroupItem>
);

export const SpecificationEditor = ({spec}) => (
    <div className={"test-configuration-view"}>
        <Panel>
            <Panel.Heading>Testing Vectors</Panel.Heading>
            <ListGroup>
                {spec.testingVectors.map((vector) => (<Vector vector={vector} specName={spec.name} key={vector.tag}/>))}
            </ListGroup>
        </Panel>
        <Panel>
            <Panel.Heading>Global Configuration</Panel.Heading>
            <Panel.Body id={"attributeGroups"}>
                {spec.attributeGroups.map((group) => 
                    <AttributeGroup scope={"specification"} specName={spec.name} group={group} key={group.groupName}/>)}
            </Panel.Body>
        </Panel>
        <br/>
        <h4>Testing Features</h4>
        <br/>
        {spec.features.map((feature) => <Feature key={feature.uri.path} feature={feature} specName={spec.name}/>)}
    </div>
)