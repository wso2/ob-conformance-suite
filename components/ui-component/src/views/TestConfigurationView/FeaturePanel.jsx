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
import {
    Panel, Row, Table,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import TestPlanReduxHelper from '../../utils/TestPlanReduxHelper';
import { toggleFeature } from '../../actions';
import AttributeGroup from '../../components/AttributeGroup';
import ScenarioDataRow from './ScenarioDataRow';

/**
 * Render feature panel for configuration of attribute groups.
 * @param {Object} feature feature
 * @param {string} specName specification name
 * @param {func} dispatch redux dispatch callback
 * @param {Object} testvalues testvaluesz object
 * @returns {React.Component} FeaturePanel
 */
const FeaturePanel = ({ feature, specName, dispatch, testvalues }) => (
    <div className='tc-feature-panel'>
        <Panel
            expanded={TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName).includes(feature.uri.path)}
            onToggle={() => { dispatch(toggleFeature(specName, feature.uri.path)); }}
        >
            <Panel.Heading onClick={() => { dispatch(toggleFeature(specName, feature.uri.path)); }}>
                <div className='pull-right tc-checkbox'>
                    <i className={'fas fa-' + (TestPlanReduxHelper.getSelectedFeaturesFromState(testvalues, specName)
                        .includes(feature.uri.path) ? 'check-square check-square-m' : 'square fa-1x fa-square-list')}
                    />
                </div>
                <Panel.Title>
                    <div className='tc-feature-panel-head'>
                        <p>
                            Execute Test
                            {feature.title}
                        </p>
                        <small><p className='text-muted'>{feature.description}</p></small>
                    </div>
                </Panel.Title>
            </Panel.Heading>
            <Panel.Collapse>
                <Panel.Body>
                    <Row>
                        <Panel className='scenario-panel' defaultExpanded={false}>
                            <Panel.Title className='scenario-panel'>
                                <Panel.Toggle className='scenario-panel-title'>Show Scenarios</Panel.Toggle>
                            </Panel.Title>
                            <Panel.Collapse>
                                <Panel.Body className='scenario-panel-body'>
                                    <Table striped bordered condensed hover>
                                        <thead>
                                            <tr align='left'>
                                                <th className='tableHead'>Scenario Name</th>
                                                <th className='tableHead'>Spec Name</th>
                                                <th className='tableHead'>Section</th>
                                            </tr>
                                        </thead>
                                        <tbody className='text-center'>
                                            {feature.scenarios.map(scenario => <ScenarioDataRow key={scenario.scenarioName} scenario={scenario} />)}
                                        </tbody>
                                    </Table>
                                </Panel.Body>
                            </Panel.Collapse>
                        </Panel>
                    </Row>
                    {feature.attributeGroups ? <hr /> : []}
                    {feature.attributeGroups
                        ? feature.attributeGroups.map(group => (
                            <AttributeGroup
                                scope='feature'
                                specName={specName}
                                featureName={feature.uri.path}
                                group={group}
                                key={group.groupName}
                            />
                        ))
                        : []}
                </Panel.Body>
            </Panel.Collapse>
        </Panel>
    </div>
);

FeaturePanel.propTypes = {
    specName: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired,
    feature: PropTypes.shape({
        uri: PropTypes.shape({
            path: PropTypes.string.isRequired,
        }).isRequired,
        title: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        attributeGroups: PropTypes.array.isRequired,
        scenarios: PropTypes.array.isRequired,
    }).isRequired,
    testvalues: PropTypes.shape().isRequired,
};

export default connect(state => ({ testvalues: state.testvalues }))(FeaturePanel);
