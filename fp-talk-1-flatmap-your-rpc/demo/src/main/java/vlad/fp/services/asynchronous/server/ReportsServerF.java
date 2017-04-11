package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.asynchronous.api.ReportsServiceF;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.BillingService;
import vlad.fp.services.synchronous.api.ReportsService;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ReportsServerF implements ReportsServiceF {
  private final ConcurrentMap<ReportID, Report> reports = new ConcurrentHashMap<>();

  private final BillingServiceF billingService;

  public ReportsServerF(BillingServiceF billingService) {
    this.billingService = billingService;
  }

  @Override
  public Task<ReportID> generateBillingReport(InvoiceID invoiceID) {
    return billingService.getInvoice(invoiceID).flatMap(invoice -> {
      ReportID reportID = new ReportID(UUID.randomUUID().toString());
      Report report = new Report("Invoice #" + invoice.id() + "\n"
          + "Sender: " + invoice.sender() + "\n"
          + "Receiver: " + invoice.report() + "\n"
          + "Credits: " + invoice.credits());

      reports.put(reportID, report);
      return Task.now(reportID);
    });
  }

  @Override
  public Task<Report> getReport(ReportID reportID) {
    return Task.now(reports.get(reportID));
  }
}
