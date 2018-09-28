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
import {connect} from "react-redux";
import RequestBuilder from "../utils/RequestBuilder";
import { addSpecification, addTestPlan, clearTestPlan } from '../actions';

const client = new RequestBuilder();

class CommonDataLoader extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
        };
    }

    componentDidMount() {
        axios.all([client.getSpecifications(), client.getTestPlans()]).then(
            axios.spread((specs, plans) => {
                specs.data.forEach((spec) => {
                    this.props.dispatch(addSpecification(spec.name, spec));
                });
                const testplans = plans.data;
                Object.keys(testplans).forEach(key => this.props.dispatch(addTestPlan(key, testplans[key].testPlan, testplans[key].reports))
                );
            }),
        ).finally(() => {
            this.setState({
                loading: false,
            });
        });
    }


    render() {
        if (this.state.loading) {
            return (<h1>Loading...</h1>);
        } else {
            return (this.props.children);
        }
    }
}

export default connect()(CommonDataLoader);
