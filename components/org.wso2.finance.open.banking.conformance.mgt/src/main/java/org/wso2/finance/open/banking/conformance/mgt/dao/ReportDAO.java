package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;

/**
 * Interface to create a ReportDAO.
 */
public interface ReportDAO {
    public int storeReport(String userID, int testID, Report report);
    public int getNewReportID(String userID, int testID);
    public void updateReport(int reportID, Report report);
    public Report getReport(int reportID);
    public List<Report> getReports(int testID);
}
