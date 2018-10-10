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
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import TestReportHelper from '../../utils/TestReportHelper';

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
                    &nbsp;%
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

export default TestPlanRow;
