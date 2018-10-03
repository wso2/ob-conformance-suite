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
import axios from 'axios';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import RequestBuilder from '../utils/RequestBuilder';
import { addSpecification, addTestPlan } from '../actions';
import LoaderComponent from './LoaderComponent';

const client = new RequestBuilder();

/**
 * Get commonly used resources to the redux store.
 * TODO: depricate usage of this class and have self contained requests.
 */
class CommonDataLoader extends React.Component {
    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
        };
    }

    /**
     * @inheritdoc
     */
    componentDidMount() {
        axios.all([client.getSpecifications(), client.getTestPlans()]).then(
            axios.spread((specs, plans) => {
                specs.data.forEach((spec) => {
                    const { dispatch } = this.props;
                    dispatch(addSpecification(spec.name, spec));
                });
                const testplans = plans.data;
                const { dispatch } = this.props;
                Object.keys(testplans).forEach(key => dispatch(addTestPlan(key,
                    testplans[key].testPlan, testplans[key].reports)));
            }),
        ).finally(() => {
            this.setState({
                loading: false,
            });
        });
    }

    /**
     * @inheritdoc
     */
    render() {
        const { loading } = this.state;
        const { children } = this.props;

        if (loading) {
            return (
                <div className='text-center page-loader-compoent'>
                    <LoaderComponent />
                </div>
            );
        } else {
            return (children);
        }
    }
}

CommonDataLoader.propTypes = {
    dispatch: PropTypes.func.isRequired,
    children: PropTypes.node,
};

CommonDataLoader.defaultProps = {
    children: [],
};

export default connect()(CommonDataLoader);
