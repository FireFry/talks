package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.ReportsService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class ReportRpcServer implements ReportsService {
  private final ExecutorService executor;
  private final ReportsService delegate;

  public ReportRpcServer(ExecutorService executor, ReportsService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public ReportID generateBillingReport(InvoiceID invoice) {
    return RpcUtils.execute(executor, () -> delegate.generateBillingReport(invoice));
  }

  @Override
  public Report getReport(ReportID reportID) {
    return RpcUtils.execute(executor, () -> delegate.getReport(reportID));
  }
}
