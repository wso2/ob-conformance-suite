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
import { setSpecValue, setFeatureValue } from '../actions';
import StringAttribute from './StringAttribute';

bootstrapUtils.addStyle(Button, 'secondary');

const TextLabelAttribute = ({ attribute }) => (
    <div>
        <p>
            {attribute.label ? (
                <b>
                    {attribute.label}
                    {' '}
                    {' '}
                </b>
            ) : []}
            {attribute.defaultValue}
        </p>
    </div>
);

TextLabelAttribute.propTypes = {
    attribute: PropTypes.shape.isRequired,
};

const LinkButtonAttribute = ({ attribute }) => (
    <div>
        <Button
            bsStyle='secondary'
            onClick={() => {
                window.open(attribute.defaultValue, '_blank');
            }}
        >
            {attribute.label}
        </Button>
    </div>
);

LinkButtonAttribute.propTypes = {
    attribute: PropTypes.shape.isRequired,
};

class AttributeGroup extends React.Component {
    constructor(props) {
        super(props);
        this.updateChange = this.updateChange.bind(this);
        this.getValue = this.getValue.bind(this);
    }

    getValue(attributeName) {
        const { scope } = this.props;
        const { testvalues } = this.props;
        const { specName } = this.props;
        const { group } = this.props;
        // const { featureName } = this.props;
        switch (scope) {
            case 'specification':
                return testvalues.specs[specName]
                    .selectedValues.specification[group.groupName][attributeName];
            case 'feature':
                return testvalues.specs[specName]
                    .selectedValues.features[this.props.featureName][group.groupName][attributeName];
            default:
                return null;
        }
    }

    updateChange(attributeName, value) {
        console.log('featurename' + this.props.featureName);
        const { scope } = this.props;
        const { dispatch } = this.props;
        const { specName } = this.props;
        const { group } = this.props;
        // const { featureName } = this.props;
        switch (scope) {
            case 'specification':
                dispatch(setSpecValue(specName, group.groupName, attributeName, value));
                return;
            case 'feature':
                dispatch(setFeatureValue(specName, this.props.featureName, group.groupName, attributeName, value));
                return;
            default:
                console.log('Default');
        }
    }

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
    specName: PropTypes.string.isRequired,
    group: PropTypes.shape({
        groupName: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        description: PropTypes.string,
        attributes: PropTypes.array.isRequired,
    }).isRequired,
    // featureName: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired,
};

export default connect(state => ({
    testvalues: state.testvalues,
}))(AttributeGroup);
