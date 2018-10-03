import React from 'react';
import {
    Table, Row, Col, Button, Panel, ButtonToolbar,
} from 'react-bootstrap';
import PropTypes from 'prop-types';
import TestPlanRow from './TestPlanRow';

/**
 * Render TestPlan History and action controls.
 */
class TestPlanView extends React.Component {
    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.state = {
            open: false,
        };
    }

    /**
     * @inheritdoc
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
