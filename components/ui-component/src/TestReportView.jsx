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
import {ListGroup, ListGroupItem, Button, Modal, Grid, Row, Col, Panel, Badge, ProgressBar, Well,} from 'react-bootstrap';
import AppHeader from './partials/AppHeader';
import '../public/css/report-style.scss';
import RequestBuilder from './utils/RequestBuilder';
import TestReportHelper from './utils/TestReportHelper';
import AttributeGroup from "./components/AttributeGroup";
import LoaderComponent from "./components/LoaderComponent"

import { updateReport } from './actions';

const client = new RequestBuilder();
const reportHelper = new TestReportHelper();

/*
 *Panels with API names
 */
const ReportSpec = connect(state => ({ specifications: state.specifications }))(({ spec, specName, specifications }) => (
  <Panel>
    <Panel.Heading className="spec-heading">
      <h2>
        {specifications.specs[specName].title}
        {' '}
        <small>
          {specifications.specs[specName].version}
          {' '}
        </small>
      </h2>
      <p className="text-muted">{specifications.specs[specName].description}</p>
    </Panel.Heading>
    <ListGroup>{spec.map(featurex => <ReportFeature feature={featurex} />)}</ListGroup>
  </Panel>
));

/*
 *Features
 */
const ReportFeature = ({ feature }) => (
  <ListGroupItem className="list-item-feature" key={feature.id}>
    <Panel className="feature-item-panel" defaultExpanded={reportHelper.getFeatureResultStatus(feature, reportHelper).status === 'Failed'}>
      <Panel.Heading>
        <div className="pull-right feature-result">
          <span className={reportHelper.getFeatureResultStatus(feature, reportHelper).class}>
            <i className={reportHelper.getFeatureResultStatus(feature, reportHelper).status === 'Passed'
              ? 'fas fa-check-circle' : 'fas fa-times-circle'}
            />
            {reportHelper.getFeatureResultStatus(feature, reportHelper).status}
          </span>
        </div>
        <Panel.Title>
          <h4 className="feature-title">
            <b>Feature:</b>
            {' '}
            {feature.name}
          </h4>
        </Panel.Title>
        <Panel.Toggle componentClass="a">View Scenarios</Panel.Toggle>
      </Panel.Heading>
      <Panel.Collapse>
        <Panel.Body>
          {feature.elements.map(element => <FeatureElement element={element} />)}
        </Panel.Body>
      </Panel.Collapse>
    </Panel>
  </ListGroupItem>
);

/*
 *Scenarios of feature
 */
const FeatureElement = ({ element }) => (
  <ListGroupItem key={element.id}>
    <h4 className="scenario-title">{element.name}</h4>
    <p>
      <span className="text-muted">Checking Compliance for </span>
      <span className="scenario-spec-details">
        <b>
          {element.tags[0].name.slice(1)}
          {' '}
            &nbsp;
        </b>
        <Badge className="spec-badge">
          {element.tags[1].name.slice(1)}
        </Badge>
      </span>
    </p>
    {stepStatus(element.steps)}
  </ListGroupItem>
);

/*
 *Steps of scenario
 */
const stepStatus = (steps) => {
  let status = true;
  let errorStep;
  let errorDescription;
  let errorClass;
  let faIconClass = '';
  const errorDisplayList = [];

  steps.forEach((step) => {
    status = status && (step.result.status === 'passed');
    errorClass = step.result.status;
    errorDescription = step.result.error_message;
    errorStep = (`${step.keyword} | ${step.name}`);
    step.result.status === 'passed' ? faIconClass = '' : faIconClass = '';

    errorDisplayList.push(
      <ListGroupItem bsStyle="" className={errorClass}>
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

        { step.result.status === 'skipped'
          ? <span className="pull-right">skipped</span>
          : <i className={faIconClass} />
                }
        { step.result.status === 'failed'
          ? (
            <Panel defaultExpanded={false} className="error-description-panel">
              <Panel.Toggle componentClass="a">
                <span className="error-more-info-link">
                  <b>{errorStep.split(' ')[0]}</b>
                  {' '}
                  {errorStep.split(' ').slice(1).join(' ')}
                  {' '}
                  <i className="fas fa-angle-down" />
                </span>
              </Panel.Toggle>
              <Panel.Collapse>
                <Panel.Body>
                  <i>
                    {errorDescription.match(new RegExp('StartError' + '(.*)' + 'EndError'))
                      ? errorDescription.match(new RegExp('StartError' + '(.*)' + 'EndError'))[1]
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
    return (<p className="passedTag status-badge"><i className="fas fa-check-circle" /></p>);
  }

  return (
    <div>
      <p className="failedTag status-badge"><i className="fas fa-times-circle" /></p>
      <Panel className="error-panel" defaultExpanded>
        <Panel.Collapse>
          <p className="top-left-padding"><b>Failure details :</b></p>
          <ListGroup>
            <Well bsSize="small">
              {errorDisplayList}
            </Well>
          </ListGroup>
        </Panel.Collapse>
      </Panel>
    </div>
  );
};

class TestReportView extends React.Component {
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


  componentDidMount() {
    client.getResultsForTestPlan(this.state.uuid, this.state.revision).then((response) => {
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
          reportHelper.getFeatureCount(response.data.testPlan)), // to check if the report is for a finished test
        progress: ((results.passed + results.failed) / (
          reportHelper.getFeatureCount(response.data.testPlan))) * 100,
      });

      /* Add Ids of loaded results to the state. */
      const finishedFeatureIdSet = this.state.finishedFeatureIds;
      for (var api in response.data.report.result) {
        finishedFeatureIdSet[api] = [];
        if (typeof (response.data.report.result[api][0]) !== 'undefined') {
          response.data.report.result[api].forEach((feature) => {
            finishedFeatureIdSet[api].push(feature.id);
          });
        }
        this.setState({ finishedFeatureIds: finishedFeatureIdSet });
      }

      /* If the test is still running, start appending results to the report */
      if (response.data.report.state === 'RUNNING') {
        this.setState({ testRunning: true });
        this.interval = setInterval(() => this.appendResults(), 2000);
      }
    });
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  /*
   *Function to load results to the report while polling
   */
  appendResults() {
    client.pollResultsForTestPlan(this.state.uuid).then((response) => {
      for (let i = 0, len = response.data.length; i < len; i++) {
        const result = response.data[i];

        /* Check for already loaded results and skip appending. */
        if (this.state.finishedFeatureIds.hasOwnProperty(result.specName)) {
          if (this.state.finishedFeatureIds[result.specName].includes(result.featureResult.id)) {
            continue;
          }
        }

        /* update state when the test is finished */
        if (result.runnerState === 'DONE') {
          client.getResultsForTestPlan(this.state.uuid, this.state.revision).then((response) => {
            this.props.dispatch(updateReport(response.data.report));
          });
          this.setState({
            testRunning: false,
          });
        }

        this.setState({
          showInteractionModel: false,
        });

        let resultObject = this.state.data;
        if (result.featureResult) {
          const featureResult = reportHelper.getFeatureResult(result.featureResult, reportHelper);
          resultObject = {
            ...resultObject,
            [result.specName]: [...resultObject[result.specName], result.featureResult],
          };
          this.setState({
            data: resultObject,
            passed: this.state.passed
                            + (featureResult.failed === 0) * 1, // all scenarios of feature passed
            failed: this.state.failed
                            + (featureResult.failed > 0) * 1, // any scenario of feature failed
            completedFeatures: this.state.completedFeatures + 1,
            progress: ((this.state.completedFeatures + 1) / this.state.featureCount) * 100,
          });
        } else if (result.attributeGroup) {
          this.setState({
            attributes: result.attributeGroup,
            showInteractionModel: true,
          });
        }
      }
    });
  }

  renderMain() {
    return (
      <Grid>
        <Modal show={this.state.showInteractionModel} onHide={() => { this.setState({ showInteractionModel: false }); }} container={this}>
          <Modal.Header closeButton>
            <Modal.Title>
                            Browser Interaction
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {this.state.attributes
              ? (
                <AttributeGroup
                  group={this.state.attributes}
                  key={this.state.attributes.groupName}
                />
              )
              : []}
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={() => { this.setState({ showInteractionModel: false }); }}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
        <Row className="stickeyHeader">
          <Col md={12}>
            <div className="pull-right">
              { this.state.testRunning
                ? <LoaderComponent />
                : this.state.failed > 0
                  ? <Badge className="test-complete-withfail-badge">Completed</Badge>
                  : <Badge className="test-complete-badge">Completed</Badge>
                            }
            </div>
            <div>
              <h1 className="report-title">
                {this.state.testName}
                {' '}
                <small>Report</small>
              </h1>
            </div>

            <div className="overall-results-block report-block">
              {this.state.passed > 0
                ? (
                  <p>
                    <span className="passed-summary">Passed: </span>
                    {this.state.passed}
                  </p>
                )
                : null
                            }

              {this.state.failed > 0
                ? (
                  <p>
                    <span className="failed-summary">Failed: </span>
                    {this.state.failed}
                  </p>
                )
                : null
                            }

              <div hidden={!this.state.newTest}>
                { this.state.progress !== 100
                  ? <ProgressBar className="pass-rate-progress" active striped bsStyle="" now={this.state.progress} />
                  : <ProgressBar className="pass-rate-progress fadeout" striped bsStyle="" now="100" />
                                }
              </div>

            </div>
          </Col>
        </Row>
        <Row>
          <Col md={12}>
            <br />
            <div>
              {Object.keys(this.state.data).map(
                key => <ReportSpec spec={this.state.data[key]} specName={key} />,
              )}
            </div>
          </Col>
        </Row>
      </Grid>
    );
  }

  render() {
    return (
      <div>
        <AppHeader />
        <br />
        {this.state.loading ? <h1>Loading..</h1> : this.renderMain()}
      </div>
    );
  }
}

export default connect()(TestReportView);
