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
import {updateSpecification} from "./actions";
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import {connect} from 'react-redux'
import axios from 'axios';

const client = new RequestBuilder();

class TestConfigurationView extends React.Component {

    componentDidMount() {
        if (this.props.specifications.selected.length !== 0) {
            axios.all(this.props.specifications.selected.map(key => client.getSingleSpecification(key))).then(
                axios.spread(response => {
                    var spec = response.data;
                    this.props.dispatch(updateSpecification(spec.name, spec));
                })
            );
        } else {
            this.props.history.push("/tests/new");
        }
    }

    renderSpecs(){
        return this.props.specifications.selected.map(specName => {
            var spec = this.props.specifications.specs[specName];
            return <h1>{spec.name}</h1>
        });
    }

    render() {
        return (
            <div>
                <AppHeader/>
                <AppBreadcrumbs/>
                <div className={"container"}>
                    <br/>
                    <Grid>
                        <Row>
                            <Col md={4}>{this.renderSpecs()}</Col>
                            <Col md={8}>
                            </Col>
                        </Row>
                    </Grid>
                </div>
            </div>
        );
    }
}

export default withRouter(connect((state) => ({
    specifications: state.specifications
}))(TestConfigurationView));