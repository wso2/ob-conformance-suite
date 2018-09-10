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
import {ListGroup, ListGroupItem, Panel, Row, Col} from 'react-bootstrap';
import {toggleVector, toggleFeature} from "../actions";
import AttributeGroup from "./AttributeGroup";


export const Feature = connect((state) => ({testvalues: state.testvalues}))(({feature, specName, dispatch, testvalues}) => (
    <Panel expanded={TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName).includes(feature.uri.path)}>
        <Panel.Heading onClick={() => {dispatch(toggleFeature(specName, feature.uri.path))}}>
            <div className="pull-right">
                <i className={"fas fa-lg fa-" + (TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName).includes(feature.uri.path) ? "check-square" : "square")}/>
            </div>
            <Panel.Title>Execute Test {feature.title}</Panel.Title>
        </Panel.Heading>
        <Panel.Collapse>
            <Panel.Body>
                <Row>
                    <Col xs={8}><b>{feature.description}</b></Col>
                    <Col xs={4} className="specdetails">AccountAPI v1.0 Section 2.5</Col>
                </Row>
                {feature.attributeGroups ? <hr/> : []}
                {feature.attributeGroups ?
                    feature.attributeGroups.map(group => <AttributeGroup scope={"feature"} specName={specName} featureName={feature.uri.path} group={group} key={group.groupName}/>) : []}
            </Panel.Body>
        </Panel.Collapse>
    </Panel>
));

export const Vector = connect((state) => ({testvalues: state.testvalues}))(({testvalues, specName, vector, dispatch}) => (
    <ListGroupItem onClick={() => {
        dispatch(toggleVector(specName, vector.tag))
    }}>
        <div className="pull-right">
            <i className={"fas fa-lg fa-" + (TestPlanReduxHelper.getSelectedVectorsFromState(testvalues, specName).includes(vector.tag) ? "check-square" : "square")}/>
        </div>
        <p><b>Test {vector.title}</b></p>
    </ListGroupItem>
));


export const Specification = ({spec,selectElement}) => (
    <ListGroup>
        <ListGroupItem key={"root-spec"} onClick={() => {selectElement(spec.name)}}>
            <div className="pull-right">
                <i className="fas fa-cog"></i>
            </div>
            <h4>{spec.title} {spec.version}</h4>
            <p>{spec.description}</p>
        </ListGroupItem>
        {
            spec.testingVectors.map((vector) => (<Vector vector={vector} specName={spec.name} key={vector.tag}/>))
        }
    </ListGroup>
);

export const SpecificationEditor = ({spec}) => (
    <Panel>
        <Panel.Heading>
            <Panel.Title componentClass="h3">Configure {spec.title} {spec.version} Test</Panel.Title>
        </Panel.Heading>
        <Panel.Body>
            <div id={"attributeGroups"}>
                {spec.attributeGroups.map((group) => <AttributeGroup scope={"specification"} specName={spec.name} group={group} key={group.groupName}/>)}
            </div>
            <br/>
            <div id={"features"}>
                {spec.features.map((feature) => <Feature key={feature.uri.path} feature={feature} specName={spec.name}/>)}
            </div>
        </Panel.Body>
    </Panel>
)