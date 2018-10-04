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
    ListGroup, Panel,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import AttributeGroup from '../../components/AttributeGroup';
import FeaturePanel from './FeaturePanel';
import VectorListGroupItem from './VectorListGroupItem';

/**
 * Editor View for a single specification.
 * @param {Object} spec specification
 * @returns {React.Component} Test Configuration View
 */
const SpecificationEditorView = ({ spec }) => (
    <div className='test-configuration-view'>
        <Panel>
            <Panel.Heading>Testing Vectors</Panel.Heading>
            <ListGroup>
                {spec.testingVectors.map(vector => (<VectorListGroupItem vector={vector} specName={spec.name} key={vector.tag} />))}
            </ListGroup>
        </Panel>
        <Panel>
            <Panel.Heading>Global Configuration</Panel.Heading>
            <Panel.Body id='attributeGroups'>
                {spec.attributeGroups.map(group =>
                    <AttributeGroup scope='specification' specName={spec.name} group={group} key={group.groupName} />)}
            </Panel.Body>
        </Panel>
        <br />
        <h4>Testing Features</h4>
        <br />
        {spec.features.map(feature => <FeaturePanel key={feature.uri.path} feature={feature} specName={spec.name} />)}
    </div>
);

SpecificationEditorView.propTypes = {
    spec: PropTypes.shape({
        name: PropTypes.string.isRequired,
        testingVectors: PropTypes.array.isRequired,
        attributeGroups: PropTypes.array.isRequired,
        features: PropTypes.array.isRequired,
    }).isRequired,
};

export default SpecificationEditorView;
