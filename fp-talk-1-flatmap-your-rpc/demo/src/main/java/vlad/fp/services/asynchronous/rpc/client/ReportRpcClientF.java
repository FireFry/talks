package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.ReportsServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;

public class ReportRpcClientF implements ReportsServiceF {
  private final NetworkF network;
  private final ReportsServiceF delegate;

  public ReportRpcClientF(NetworkF network, ReportsServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<ReportID> generateBillingReport(InvoiceID invoice) {
    return network.execute(() -> delegate.generateBillingReport(invoice));
  }

  @Override
  public Task<Report> getReport(ReportID reportID) {
    return network.execute(() -> delegate.getReport(reportID));
  }
}
