package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ReportsServer implements ReportsService {
  private final ConcurrentMap<ReportID, Report> reports = new ConcurrentHashMap<>();

  private final BillingService billingService;

  public ReportsServer(BillingService billingService) {
    this.billingService = billingService;
  }

  @Override
  public ReportID generateBillingReport(InvoiceID invoiceID) {
    Invoice invoice = billingService.getInvoice(invoiceID);
    ReportID reportID = new ReportID(UUID.randomUUID().toString());
    Report report = new Report("Invoice #" + invoice.id() + "\n"
        + "Sender: " + invoice.sender() + "\n"
        + "Receiver: " + invoice.report() + "\n"
        + "Credits: " + invoice.credits());

    reports.put(reportID, report);
    return reportID;
  }

  @Override
  public Report getReport(ReportID reportID) {
    return reports.get(reportID);
  }
}
