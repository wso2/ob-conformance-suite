package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;

/**
 * Interface to create a ReportDAO
 */
public interface ReportDAO {
    public void storeReport(String userID, String uuid, Report report);
    public Report getReport(String userID, String uuid, int reportID);
    public List<Report> getReports(String userID, String uuid);
}
