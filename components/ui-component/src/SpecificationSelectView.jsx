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
import {ListGroup, ListGroupItem, Glyphicon, Button} from 'react-bootstrap';

const client = new RequestBuilder();

export default class SpecificationSelectView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            specifications: [],
            selectedSpecifications: {}
        }
    }

    componentDidMount() {
        client.getSpecifications().then((response) => {
            this.setState({
                loading: false,
                specifications: response.data
            })
        })
    }

    render() {
        return (
            <div>
                <AppHeader/>
                <div className={"container"}>
                    {this.state.loading ? <h1>Loading</h1> : <div>
                        {this.renderMain()}
                    </div>}
                </div>
            </div>
        )
    }

    renderSpec(specification) {
        return (
            <ListGroupItem key={specification.name} header={specification.title} onClick={()=>{this.toggleSpec(specification)}}>
                <Glyphicon className={"pull-right"} glyph={specification.name in this.state.selectedSpecifications ? "ok" : "plus"}/>
                {specification.description}
            </ListGroupItem>);
    }

    toggleSpec(specification){
        var ref = this.state.selectedSpecifications;
        if(specification.name in ref){
            delete this.state.selectedSpecifications[specification.name];
        }else{
            ref[specification.name] = specification;
        }
        this.setState({
            selectedSpecifications:ref
        });
    }

    isEmptySelection(){
        return Object.keys(this.state.selectedSpecifications).length === 0;
    }

    renderMain() {
        return (
            <div>
                <h1>Available Tests</h1>
                <hr/>
                <ListGroup>
                    <ListGroupItem disabled>
                        <b>Available Specifications</b>
                    </ListGroupItem>
                    {this.state.specifications.map((spec) => {
                        return this.renderSpec(spec)
                    })}
                </ListGroup>
                <div className={"text-center"}>
                    <Button bsStyle={"primary"} bsSize={"lg"} disabled={this.isEmptySelection()}>Continue</Button>
                </div>
            </div>
        );
    }
}
