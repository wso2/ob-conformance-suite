/* eslint-disable no-trailing-spaces */
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
import { 
    ListGroup, ListGroupItem, Button, Modal, 
    Grid, Row, Col, Panel, Badge, ProgressBar, Well, 
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import AppHeader from '../../partials/AppHeader';
import RequestBuilder from '../../utils/RequestBuilder';
import TestReportHelper from '../../utils/TestReportHelper';
import AttributeGroup from '../../components/AttributeGroup';
import LoaderComponent from '../../components/LoaderComponent';
import { updateReport } from '../../actions';


const client = new RequestBuilder();
const reportHelper = new TestReportHelper();


/*
 *Steps of scenario
 */
const stepStatus = (steps) => {
    let status = true;
    let errorStep;
    let errorDescription;
    let errorClass;
    const errorDisplayList = [];

    steps.forEach((step) => {
        status = status && (step.result.status === 'passed');
        errorClass = step.result.status;
        errorDescription = step.result.error_message;
        errorStep = (`${step.keyword} | ${step.name}`);
        errorDisplayList.push(
            <ListGroupItem className={errorClass} key={step.name}>
                { step.result.status !== 'failed'
                    ? (
                        <span>
                            <b>{errorStep.split(' ')[0]}</b>
                            {' '}
                            {errorStep.split(' ').slice(1).join(' ')}

                        </span>
                    )
                    : null
                }

                {step.result.status === 'skipped'
                    ? <span className='pull-right'>skipped</span>
                    : null
                }

                {step.result.status === 'failed'
                    ? (
                        <Panel defaultExpanded={false} className='error-description-panel'>
                            <Panel.Toggle componentClass='a'>
                                <span className='error-more-info-link'>
                                    <b>{errorStep.split(' ')[0]}</b>
                                    {errorStep.split(' ').slice(1).join(' ')}
                                    <i className='fas fa-angle-down' />
                                </span>
                            </Panel.Toggle>
                            <Panel.Collapse>
                                <Panel.Body>
                                    <i>
                                        {errorDescription.match(new RegExp('StartError(.*)EndError'))
                                            ? errorDescription.match(new RegExp('StartError(.*)EndError'))[1]
                                            : errorDescription
                                        }
                                    </i>
                                </Panel.Body>
                            </Panel.Collapse>
                        </Panel>
                    )
                    : null
                }
            </ListGroupItem>,
        );
    });

    if (status) {
        return (<p className='passedTag status-badge'><i className='fas fa-check-circle' /></p>);
    } else {
        return (
            <div>
                <p className='failedTag status-badge'><i className='fas fa-times-circle' /></p>
                <Panel className='error-panel' defaultExpanded>
                    <Panel.Collapse>
                        <p className='top-left-padding'><b>Failure details :</b></p>
                        <ListGroup>
                            <Well bsSize='small'>
                                {errorDisplayList}
                            </Well>
                        </ListGroup>
                    </Panel.Collapse>
                </Panel>
            </div>
        );
    }
};

/*
 *Features
 */
const ReportFeature = ({ feature }) => (
    <ListGroupItem className='list-item-feature' key={feature.id}>
        <Panel 
            className='feature-item-panel'
            defaultExpanded={reportHelper.getFeatureResultStatus(feature, reportHelper).status === 'Failed'}
        >
            <Panel.Heading>
                <div className='pull-right feature-result'>
                    <span className={reportHelper.getFeatureResultStatus(feature, reportHelper).class}>
                        <i className={reportHelper.getFeatureResultStatus(feature, reportHelper).status === 'Passed'
                            ? 'fas fa-check-circle' : 'fas fa-times-circle'}
                        />
                        {' '}
                        {reportHelper.getFeatureResultStatus(feature, reportHelper).status}
                    </span>
                </div>
                <Panel.Title>
                    <h4 className='feature-title'>
                        <b>Feature: </b>
                        {feature.name}
                    </h4>
                </Panel.Title>
                <Panel.Toggle componentClass='a'>View Scenarios</Panel.Toggle>
            </Panel.Heading>
            <Panel.Collapse>
                <Panel.Body>
                    {feature.elements.map(element => <FeatureElement key={element.id} element={element} />)}
                </Panel.Body>
            </Panel.Collapse>
        </Panel>
    </ListGroupItem>
);

ReportFeature.propTypes = {
    feature: PropTypes.shape({ 
        line: PropTypes.number.isRequired,
        elements: PropTypes.array.isRequired,
        name: PropTypes.string.isRequired,
        id: PropTypes.string.isRequired,
        keyword: PropTypes.string.isRequired,
        uri: PropTypes.string.isRequired,
    }).isRequired,
};

/*
 *Scenarios of feature
 */
const FeatureElement = ({ element }) => (
    <ListGroupItem key={element.id}>
        <h4 className='scenario-title'>{element.name}</h4>
        <p>
            <span className='text-muted'>Checking Compliance for </span>
            <span className='scenario-spec-details'>
                <b>
                    {element.tags[0].name.slice(1)}
                    &nbsp;
                </b>
                <Badge className='spec-badge'>
                    {element.tags[1].name.slice(1)}
                </Badge>
            </span>
        </p>
        {stepStatus(element.steps)}
    </ListGroupItem>
);

FeatureElement.propTypes = {
    element: PropTypes.shape({ 
        line: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        id: PropTypes.string.isRequired,
        type: PropTypes.string.isRequired,
        keyword: PropTypes.string.isRequired,
        steps: PropTypes.array.isRequired,
        tags: PropTypes.array.isRequired,
    }).isRequired,
};

/**
 *Panels with API names
 */
 
const ReportAPI = connect(state => ({ specifications: state.specifications }))(({ api, apiName, specifications }) => (
    <Panel>
        <Panel.Heading className='api-heading'>
            <h2>
                {specifications.specs[apiName].title}
                {' '}
                <small>
                    {specifications.specs[apiName].version}
                    {' '}
                </small>
            </h2>
            <p className='text-muted'>{specifications.specs[apiName].description}</p>
        </Panel.Heading>
        <ListGroup>{ api.map(feature => <ReportFeature feature={feature} key={feature.id} />) }</ListGroup>
    </Panel>
));

/**
 * ClassName: TestReportView
 * 
 * Responsible for displaying results and statistical 
 * data relavant to the results which will be supplied 
 * as JSON data.  
 *
 */
class TestReportView extends React.Component {
    /**
     * 
     * @param {*} props - Class props
     */
    constructor(props) {
        super(props);
        this.state = {
            uuid: props.match.params.uuid,
            revision: props.match.params.revision,
            loading: true,
            data: null,
            passed: 0,
            failed: 0,
            attributes: null,
            showInteractionModel: null,
            testRunning: false,
            progress: 0,
            completedFeatures: 0,
            featureCount: 0,
            finishedFeatureIds: {},
        };

        this.interval = null;
        this.renderMain = this.renderMain.bind(this);
        this.appendResults = this.appendResults.bind(this);
    }


    /**
     * @inheritdoc
     */
    componentDidMount() {
        const { uuid, revision } = this.state;
        client.getResultsForTestPlan(uuid, revision).then((response) => {
            const report = response.data.report.result;
            const results = reportHelper.getTestSummary(report);
            this.setState({
                loading: false,
                data: report,
                passed: results.passed,
                failed: results.failed,
                completedFeatures: results.passed + results.failed,
                featureCount: (reportHelper.getFeatureCount(response.data.testPlan)),
                testName: response.data.testPlan.name,
                newTest: (results.passed + results.failed) < (
                    reportHelper.getFeatureCount(response.data.testPlan)), // finished test?
                progress: ((results.passed + results.failed) / (
                    reportHelper.getFeatureCount(response.data.testPlan))) * 100,
            });
            /* Add Ids of loaded results to the state. */
            const { finishedFeatureIds } = this.state;
            for (const api in response.data.report.result) {
                if (Object.prototype.hasOwnProperty.call(response.data.report.result, api)) {
                    finishedFeatureIds[api] = [];
                    if (typeof (response.data.report.result[api][0]) !== 'undefined') {
                        response.data.report.result[api].forEach((feature) => {
                            finishedFeatureIds[api].push(feature.id);
                        });
                    }
                    this.setState({ finishedFeatureIds });
                }
            }

            /* If the test is still running, start appending results to the report */
            if (response.data.report.state === 'RUNNING') {
                this.setState({ testRunning: true });
                this.interval = setInterval(() => this.appendResults(), 2000);
            }
        });
    }

    /**
     * @inheritdoc
     */
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    /**
     * @returns {boolean} - Are results loading?
     */
    isLoading() {
        const { loading } = this.state;
        return loading;
    }

    /**
     * Loads results to the report while polling
     */
    appendResults() {
        const { uuid } = this.state;
        client.pollResultsForTestPlan(uuid).then((pollResponse) => {
            for (let i = 0, len = pollResponse.data.length; i < len; i++) {
                const result = pollResponse.data[i];
                /* Check for already loaded results and skip appending. */
                const { finishedFeatureIds } = this.state;
                /* Show browser interaction modal if an arrtibute group is received */
                if (result.attributeGroup) {
                    this.setState({
                        attributes: result.attributeGroup,
                        showInteractionModel: true,
                    });
                } else if (!(finishedFeatureIds[result.specName].includes(result.featureResult.id))) {
                    this.setState({
                        showInteractionModel: false,
                    });

                    let { data } = this.state; // result object

                    const featureResult = reportHelper.getFeatureResult(result.featureResult, reportHelper);
                    data = {
                        ...data,
                        [result.specName]: [...data[result.specName], result.featureResult],
                    };
                    this.setState(prevState => ({
                        data,
                        passed: prevState.passed
                            + (featureResult.failed === 0), // all scenarios of feature passed
                        failed: prevState.failed
                            + (featureResult.failed > 0), // any scenario of feature failed
                        completedFeatures: prevState.completedFeatures + 1,
                        progress: ((prevState.completedFeatures + 1) / prevState.featureCount) * 100,
                    }));
                }

                const { revision } = this.state;
                client.getResultsForTestPlan(uuid, revision).then((response) => {
                    const { dispatch } = this.props;
                    dispatch(updateReport(response.data.report));
                });
                
                /* update state when the test is finished */
                if (result.runnerState === 'DONE') {
                    this.setState({
                        testRunning: false,
                    });
                }
            }
        });
    }

    /**
     * Renders main components of the report
     * @param {object} state - state object
     * @returns {string} - HTML markup for the main components
     */
    renderMain(state) {
        return (
            <Grid>
                <Modal 
                    show={state.showInteractionModel} 
                    onHide={() => {
                        this.setState({ showInteractionModel: false });
                    }} 
                    container={this}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>
                            Browser Interaction
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {state.attributes
                            ? (
                                <AttributeGroup
                                    group={state.attributes}
                                    key={state.attributes.groupName}
                                />
                            )
                            : []}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => {
                            this.setState({ showInteractionModel: false });
                        }}
                        >
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
                <Row className='stickeyHeader'>
                    <Col md={12}>
                        <div className='pull-right'>
                            {state.testRunning
                                ? <LoaderComponent />
                                : <Badge className='test-complete-badge'>Completed</Badge>
                            }
                        </div>
                        <div>
                            <h1 className='report-title'>
                                {state.testName}
                                {' '}
                                <small>Report</small>
                            </h1>
                        </div>

                        <div className='overall-results-block report-block'>
                            {state.passed > 0
                                ? (
                                    <p>
                                        <span className='passed-summary'>
                                            Passed:
                                            {' '}
                                            {state.passed}
                                        </span>
                                    </p>
                                )
                                : null
                            }

                            {state.failed > 0
                                ? (
                                    <p>
                                        <span className='failed-summary'>
                                            Failed:
                                            {' '}
                                            {state.failed}
                                        </span>
                                    </p>
                                )
                                : null
                            }
                            <div hidden={!state.newTest}>
                                {state.progress !== 100
                                    ? (
                                        <ProgressBar 
                                            className='pass-rate-progress' 
                                            active 
                                            striped
                                            now={state.progress}
                                        />
                                    )
                                    : <ProgressBar className='pass-rate-progress fadeout' striped now={100} />
                                }
                            </div>

                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col md={12}>
                        <br />
                        <div>
                            {Object.keys(state.data).map(
                                key => <ReportAPI api={state.data[key]} key={key} apiName={key} />,
                            )}
                        </div>
                    </Col>
                </Row>
            </Grid>
        );
    }

    /**
     * Renders the report if not in loading state
     * @returns {string} - HTML markup for the report
     */
    render() {
        return (
            <div>
                <AppHeader />
                <br />
                {this.isLoading() ? <h1>Loading..</h1> : this.renderMain(this.state)}
            </div>
        );
    }
}

TestReportView.propTypes = {
    match: PropTypes.shape({
        isExact: PropTypes.bool.isRequired,
        params: PropTypes.object.isRequired,
        path: PropTypes.string.isRequired,
        url: PropTypes.string.isRequired,
    }).isRequired,
    dispatch: PropTypes.func.isRequired,
};

export default connect()(TestReportView);
