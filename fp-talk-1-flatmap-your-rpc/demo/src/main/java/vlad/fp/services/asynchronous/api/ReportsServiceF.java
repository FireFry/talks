package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;

public interface ReportsServiceF {

  Task<ReportID> generateBillingReport(InvoiceID invoice);

  Task<Report> getReport(ReportID reportID);

}
