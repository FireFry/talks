package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.EmailServiceF;
import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;

import java.util.concurrent.ExecutorService;

public class EmailRpcServerF implements EmailServiceF {
  private final ExecutorService pool;
  private final EmailServiceF delegate;

  public EmailRpcServerF(ExecutorService pool, EmailServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<Void> sendReport(EmailAddress emailAddress, ReportID reportID) {
    return Task.fork(() -> delegate.sendReport(emailAddress, reportID), pool);
  }
}
