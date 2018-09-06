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
import AppBreadcrumbs from "./partials/AppBreadcrumbs";
import {connect} from 'react-redux'
import {addSpecification, toggleSpecification, addTestPlan} from "./actions";
import {withRouter, Link} from 'react-router-dom'
import {Table, Row, Col, Button, Glyphicon} from 'react-bootstrap';
import RequestBuilder from './utils/RequestBuilder';
const client = new RequestBuilder();



const TestPlanRow = ({testPlan}) => (
    <tr>
        <td>Jacob</td>
        <td>{testPlan.status}</td>
        <td>{JSON.stringify(testPlan)}</td>
    </tr>
);

class TestHistoryView extends React.Component{

    constructor(props){
        super(props);
        console.log(props);
    }

    componentDidMount(){
        client.getTestPlans().then((response) => {
            var data = response.data;
            Object.keys(data).map((key) => {
                this.props.dispatch(addTestPlan(key,data[key].testPlan,data[key].status));
            })
        });
    }

    render(){
        return(
            <div>
                <AppHeader/>
                <AppBreadcrumbs/>
                <div className={"divStyle"}>
                    <div className={"headStyle"}>Organization</div>
                    <div className={"subHeadStyle"}>
                        <Row className="show-grid">
                            <Col xs={8}>Test History</Col>
                            <Col xs={4}>
                                <Link to={"/tests/new"}>
                                    <Button className="pull-right" bsStyle="default">
                                        + New Test
                                    </Button>
                                </Link>
                            </Col>
                        </Row>
                    </div>
                    <Table striped bordered condensed hover>
                        <thead>
                            <tr>
                            <th className={"tableHead"}>Specification</th>
                            <th className={"tableHead"}>Status</th>
                            <th className={"tableHead"}>Results</th>
                            </tr>
                        </thead>
                        <tbody className={"text-center"}> 
                            {Object.values(this.props.testplans).map((plan) => <TestPlanRow testPlan={plan}/> )}
                        </tbody>
                    </Table>
                </div>
            </div>
        );
    }
}


export default withRouter(connect((state) => ({
    testplans : state.testplans.testplans
}))(TestHistoryView));