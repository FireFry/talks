package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;

public interface ReportsService {

  ReportID generateBillingReport(InvoiceID invoice);

  Report getReport(ReportID reportID);

}
