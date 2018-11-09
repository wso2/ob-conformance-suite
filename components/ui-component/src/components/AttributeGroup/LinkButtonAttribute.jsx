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
import PropTypes from 'prop-types';

/**
 * Render link button for interations.
 * @param {Object} attribute atttribute object.
 * @returns {React.Component} LinkButton.
 */
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
    attribute: PropTypes.shape().isRequired,
};

export default LinkButtonAttribute;
