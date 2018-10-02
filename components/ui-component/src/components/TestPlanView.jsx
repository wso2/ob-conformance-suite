import React from 'react';
import { Link } from 'react-router-dom';
import {
    Table, Row, Col, Button, Panel, ButtonToolbar,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import TestReportHelper from '../utils/TestReportHelper';
import '../../public/css/report-style.scss';

const reportHelper = new TestReportHelper();

/*
 * Row structure of the test plan table
 */
const TestPlanRow = ({ report }) => (
    <tr align='left'>
        <td>{report.executed}</td>
        <td>{report.state}</td>
        <td className='overall-results-block'>
            <p>
                <span style={{ color: 'green' }}>
                    <i className='fas fa-check-circle' />
                    {' '}
                    Passed :
                    {reportHelper.getTestSummary(report.result).passed}
                </span>
            </p>
            {reportHelper.getTestSummary(report.result).failed > 0 ? (
                <p>
                    <span style={{ color: 'red' }}>
                        <i className='fas fa-times-circle' />
                        {' '}
                        Failed :
                        {reportHelper.getTestSummary(report.result).failed}
                    </span>
                </p>
            )
                : null
            }
            <p>
                <span>
                    Success Rate:
                    {reportHelper.getTestSummary(report.result).rate}
                    {' '}
%
                </span>
            </p>
        </td>
        <td>
            <Link to={
                {
                    pathname: '/tests/report/' + report.testId + '/' + report.reportId,
                    state: { fromDashboard: true },
                }
            }
            >
            Check Report
            </Link>
        </td>
    </tr>
);

TestPlanRow.propTypes = {
    report: PropTypes.shape({
        executed: PropTypes.string.isRequired,
        reportId: PropTypes.number.isRequired,
        result: PropTypes.object.isRequired,
        state: PropTypes.string.isRequired,
        testId: PropTypes.string.isRequired,
    }).isRequired,
};

/**
 * ClassName: TestPlanView
 *
 * Responsible for displaying Test details and summary in the
 * test history view
 *
 */
class TestPlanView extends React.Component {
    /**
     *
     * @param {*} props - Class props
     */
    constructor(props) {
        super(props);
        this.state = {
            open: false,
        };
    }

    /**
     *
     * @returns {string} - HTML markup for the TestPlanView
     */
    render() {
        const { open } = this.state;
        const { plan, specifications, runTest } = this.props;
        return (
            <Panel defaultExpanded={false} expanded={open} onToggle={() => this.togglePlan()}>
                <Panel.Heading>
                    <Panel.Title>
                        <Row className='history-view-row'>
                            <Col xs={6}>
                                <div>
                                    {plan.testPlan.name}
                                    <small>
                                        <p className='text-muted'>
                                            <span className='history-view-inline-specs'>
                                                {Object.keys(plan.testPlan.specifications).map(key => (
                                                    <span key={specifications[key].title}>
                                                        {specifications[key].title}
                                                        {specifications[key].version}
                                                    </span>
                                                ))}
                                            </span>
                                        </p>
                                    </small>
                                </div>
                            </Col>
                            <Col xs={5}>
                                <ButtonToolbar className='pull-right'>
                                    <Button
                                        onClick={() => { runTest(plan); }}
                                        className='round-btn'
                                    >
                                        <i className='fas fa-lg fa-play' />
                                    </Button>
                                    <Button className='round-btn'>
                                        <i className='fas fa-lg fa-cog' />
                                    </Button>
                                    <Button className='round-btn'>
                                        <i className='fas fa-lg fa-trash' />
                                    </Button>
                                </ButtonToolbar>
                            </Col>
                            <Col xs={1}>
                                <Button className='round-btn' onClick={() => this.setState({ open: !open })}>
                                    <i className={'fas fa-lg fa-' + (open ? 'angle-up' : 'angle-down')} />
                                </Button>
                            </Col>
                        </Row>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Collapse>
                    <Panel.Body collapsible>
                        <b>Test Iterations</b>
                        <Table className='test-history-table' striped bordered condensed hover>
                            <thead>
                                <tr>
                                    <th className='tableHead'>Test Run Date</th>
                                    <th className='tableHead'>Status</th>
                                    <th className='tableHead'>Summary</th>
                                    <th className='tableHead' />
                                </tr>
                            </thead>
                            <tbody className='text-center'>
                                {Object.values(plan.reports).map(
                                    report => <TestPlanRow key={report.reportId} report={report} />,
                                )}
                            </tbody>
                        </Table>
                    </Panel.Body>
                </Panel.Collapse>
            </Panel>
        );
    }
}

TestPlanView.propTypes = {
    plan: PropTypes.shape({
        reports: PropTypes.object.isRequired,
        testId: PropTypes.string.isRequired,
        testPlan: PropTypes.object.isRequired,
    }).isRequired,
    runTest: PropTypes.func.isRequired,
    specifications: PropTypes.shape().isRequired,
};

export default TestPlanView;
