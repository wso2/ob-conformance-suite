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
import AppHeader from "./partials/AppHeader";
import RequestBuilder from './utils/RequestBuilder';
import {withRouter} from 'react-router-dom'
import {ListGroup, ListGroupItem, Glyphicon, Button, Grid, Row, Col} from 'react-bootstrap';
import {addSpecificationToTestPlan, clearTestPlan} from "./actions";
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import {connect} from 'react-redux'
import axios from 'axios';

const client = new RequestBuilder();

class TestConfigurationView extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            loading : true
        }
    }
    componentDidMount() {
        if (this.props.specifications.selected.length !== 0) {
            this.props.dispatch(clearTestPlan());
            axios.all(this.props.specifications.selected.map(key => client.getSingleSpecification(key))).then(
                axios.spread(response => {
                    var spec = response.data;
                    this.props.dispatch(addSpecificationToTestPlan(spec));
                })
            ).finally(() => {
                this.setState({
                    loading: false
                });
            });
        } else {
            this.props.history.push("/tests/new");
        }
    }

    renderSpecs(){
        return this.props.testplan.specs.map(spec => {
            return <h1>{spec.name}</h1>
        });
    }

    renderMain(){
        return (
            <div>
                <br/>
                <Grid>
                    <Row>
                        <Col md={4}>{this.renderSpecs()}</Col>
                        <Col md={8}>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }

    render() {
        return (
            <div>
                <AppHeader/>
                <AppBreadcrumbs/>
                <div className={"container"}>
                    {this.state.loading ? <h1>Loading</h1> : this.renderMain()}
                </div>
            </div>
        );
    }
}

export default withRouter(connect((state) => ({
    specifications: state.specifications,
    testplan: state.testplan
}))(TestConfigurationView));