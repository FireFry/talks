package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.EmailService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class EmailRpcServer implements EmailService {
  private final ExecutorService executor;
  private final EmailService delegate;

  public EmailRpcServer(ExecutorService executor, EmailService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public void sendReport(EmailAddress emailAddress, ReportID reportID) {
    RpcUtils.run(executor, () -> delegate.sendReport(emailAddress, reportID));
  }
}
