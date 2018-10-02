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
    ControlLabel, FormControl, FormGroup, HelpBlock,
} from 'react-bootstrap';
import PropTypes from 'prop-types';


class StringAttribute extends React.Component {
    constructor(props) {
        super(props);

        const defaultValue = this.getDefaultValue();
        this.state = {
            value: defaultValue || '',
        };

        this.handleChange = this.handleChange.bind(this);
        this.getValidationStatus = this.getValidationStatus.bind(this);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        const { specName } = this.props;
        if (prevProps.specName !== specName) {
            const defaultValue = this.getDefaultValue();
            this.setState({
                value: defaultValue || '',
            });
        }
    }

    getDefaultValue() {
        const { attribute } = this.props;
        const { getValue } = this.props;
        const testStateValue = getValue(attribute.name);
        return testStateValue || attribute.defaultValue;
    }

    getValidationStatus() {
        const { attribute } = this.props;
        if (attribute.validationRegex) {
            const isValid = this.state.value.match(RegExp(attribute.validationRegex));
            return isValid ? null : 'error';
        } else {
            return null;
        }
    }

    handleChange(e) {
        const { updateChange } = this.props;
        const { attribute } = this.props;
        this.setState({ value: e.target.value });
        updateChange(attribute.name,
            (this.getValidationStatus() === 'success' ? this.state.value : null));
    }

    render() {
        const { attribute } = this.props;
        return (
            <FormGroup controlId={attribute.name} validationState={this.getValidationStatus()}>
                <ControlLabel>{attribute.label}</ControlLabel>
                <FormControl
                    type='text'
                    value={this.state.value}
                    placeholder='Enter text'
                    onChange={this.handleChange}
                />
                <FormControl.Feedback />
                <div hidden={this.getValidationStatus() !== 'error'} className='text-warning'>
                    <HelpBlock>{attribute.helpText}</HelpBlock>
                </div>
            </FormGroup>
        );
    }
}

StringAttribute.propTypes = {
    specName: PropTypes.string.isRequired,
    attribute: PropTypes.shape({
        name: PropTypes.string.isRequired,
        defaultValue: PropTypes.string.isRequired,
        validationRegex: PropTypes.string,
        label: PropTypes.string.isRequired,
        helpText: PropTypes.string.isRequired,
    }).isRequired,
    updateChange: PropTypes.func.isRequired,
    getValue: PropTypes.func.isRequired,
};

export default StringAttribute;
