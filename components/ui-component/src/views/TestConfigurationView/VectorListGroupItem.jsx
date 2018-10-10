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
import { ListGroupItem } from 'react-bootstrap';
import PropTypes from 'prop-types';
import TestPlanReduxHelper from '../../utils/TestPlanReduxHelper';
import { toggleVector } from '../../actions';

/**
 * Render list group item for vector selection.
 * @param {Object} testvalues testvalues
 * @param {string} specName specification name
 * @param {Object} vector vector object
 * @param {func} dispatch redux dispatch callback
 * @returns {React.Component} ListGroupItem
 */
export const Vector = (({ testvalues, specName, vector, dispatch }) => (
    <ListGroupItem onClick={() => { dispatch(toggleVector(specName, vector.tag)); }}>
        <div className='pull-right'>
            <i className={'fas fa-' + (TestPlanReduxHelper.getSelectedVectorsFromState(testvalues, specName)
                .includes(vector.tag) ? 'check-square check-square-m' : 'square fa-1x fa-square-list')}
            />
        </div>
        <p>
            Test
            {vector.title}
        </p>
    </ListGroupItem>
));


Vector.propTypes = {
    testvalues: PropTypes.shape().isRequired,
    specName: PropTypes.string.isRequired,
    vector: PropTypes.shape().isRequired,
    dispatch: PropTypes.func.isRequired,
};

export default connect(state => ({ testvalues: state.testvalues }))(Vector);
