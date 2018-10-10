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
    Button,
} from 'react-bootstrap';
import { connect } from 'react-redux';
import { bootstrapUtils } from 'react-bootstrap/lib/utils';
import PropTypes from 'prop-types';
import { setSpecValue, setFeatureValue } from '../../actions';
import StringAttribute from './StringAttribute';
import TextLabelAttribute from './TextLabelAttribute';
import LinkButtonAttribute from './LinkButtonAttribute';


bootstrapUtils.addStyle(Button, 'secondary');

/**
 * Render AttributeGroups and bind results with redux.
 */
class AttributeGroup extends React.Component {
    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.updateChange = this.updateChange.bind(this);
        this.getValue = this.getValue.bind(this);
    }

    /**
     * Get default or value from redux store for the given attribute.
     * @param {String} attributeName attribute name
     * @returns {String} attribute value
     */
    getValue(attributeName) {
        const { scope, testvalues, specName } = this.props;
        const { group, featureName } = this.props;

        switch (scope) {
            case 'specification':
                return testvalues.specs[specName]
                    .selectedValues.specification[group.groupName][attributeName];
            case 'feature':
                return testvalues.specs[specName]
                    .selectedValues.features[featureName][group.groupName][attributeName];
            default:
                return null;
        }
    }

    /**
     * Update value of redux store for the changed attribute.
     * @param {String} attributeName attribute name
     * @param {String} value attribute value
     */
    updateChange(attributeName, value) {
        const { scope, group, specName } = this.props;
        const { featureName, dispatch } = this.props;

        switch (scope) {
            case 'specification':
                dispatch(setSpecValue(specName, group.groupName, attributeName, value));
                break;
            case 'feature':
                dispatch(setFeatureValue(specName, featureName, group.groupName, attributeName, value));
                break;
            default:
                break;
        }
    }

    /**
     * Render specific Attribute based on type.
     * @param {Object} attribute Attribute object
     * @returns {React.Component} Attribute Form object
     */
    renderAttribute(attribute) {
        const { specName } = this.props;
        switch (attribute.attributeType) {
            case 'String':
                return (
                    <StringAttribute
                        attribute={attribute}
                        key={attribute.name}
                        updateChange={this.updateChange}
                        getValue={this.getValue}
                        specName={specName}
                    />
                );
            case 'TextLabel':
                return <TextLabelAttribute attribute={attribute} key={attribute.name} />;
            case 'LinkButton':
                return <LinkButtonAttribute attribute={attribute} key={attribute.name} />;
            default:
                return <p>Not A Valid Field Type</p>;
        }
    }

    /**
     * @inheritdoc
     */
    render() {
        const { group } = this.props;
        return (
            <div className='attribute-group'>
                <h4 className='sub-heading'>{group.title}</h4>
                {group.description ? <p>{group.description}</p> : []}
                {group.attributes.map(attribute => this.renderAttribute(attribute))}
            </div>
        );
    }
}

AttributeGroup.propTypes = {
    scope: PropTypes.string.isRequired,
    testvalues: PropTypes.shape({
        specs: PropTypes.object.isRequired,
    }).isRequired,
    specName: PropTypes.string,
    group: PropTypes.shape({
        groupName: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        description: PropTypes.string,
        attributes: PropTypes.array.isRequired,
    }).isRequired,
    featureName: PropTypes.string,
    dispatch: PropTypes.func.isRequired,
};

AttributeGroup.defaultProps = {
    featureName: undefined,
    specName: undefined,
};

export default connect(state => ({
    testvalues: state.testvalues,
}))(AttributeGroup);
