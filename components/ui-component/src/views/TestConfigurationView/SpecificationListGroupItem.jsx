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
    ListGroupItem,
} from 'react-bootstrap';
import PropTypes from 'prop-types';

/**
 * Render list group item for specification selector.
 * @param {Object} spec specification
 * @param {func} selectElement on element select callback
 * @param {bool} selected is selected flag
 * @returns {ListGroupItem} specification list group item
 */
const SpecificationListGroupItem = ({ spec, selectElement, selected }) => (
    <ListGroupItem key='root-spec' onClick={() => { selectElement(spec.name); }} active={selected}>
        <h4>
            {spec.title}
            &nbsp;
            <small>{spec.version}</small>
        </h4>
        <p>{spec.description}</p>
    </ListGroupItem>
);

SpecificationListGroupItem.propTypes = {
    spec: PropTypes.shape({
        name: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        version: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
    }).isRequired,
    selectElement: PropTypes.func.isRequired,
    selected: PropTypes.bool.isRequired,
};

export default SpecificationListGroupItem;
