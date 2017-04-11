package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.FrontendServiceF;
import vlad.fp.services.asynchronous.api.ReportsServiceF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Password;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;

import java.util.concurrent.ExecutorService;

public class ReportRpcServerF implements ReportsServiceF {
  private final ExecutorService pool;
  private final ReportsServiceF delegate;

  public ReportRpcServerF(ExecutorService pool, ReportsServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<ReportID> generateBillingReport(InvoiceID invoice) {
    return Task.fork(() -> delegate.generateBillingReport(invoice), pool);
  }

  @Override
  public Task<Report> getReport(ReportID reportID) {
    return Task.fork(() -> delegate.getReport(reportID), pool);
  }
}
