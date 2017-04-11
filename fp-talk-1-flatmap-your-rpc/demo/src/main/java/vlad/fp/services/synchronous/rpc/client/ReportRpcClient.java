package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.ReportsService;
import vlad.fp.services.synchronous.rpc.Network;

public class ReportRpcClient implements ReportsService {
  private final Network network;
  private final ReportsService delegate;

  public ReportRpcClient(Network network, ReportsService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public ReportID generateBillingReport(InvoiceID invoice) {
    return network.execute(() -> delegate.generateBillingReport(invoice));
  }

  @Override
  public Report getReport(ReportID reportID) {
    return network.execute(() -> delegate.getReport(reportID));
  }
}
