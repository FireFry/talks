package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.EmailServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;

public class EmailRpcClientF implements EmailServiceF {
  private final NetworkF network;
  private final EmailServiceF delegate;

  public EmailRpcClientF(NetworkF network, EmailServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<Void> sendReport(EmailAddress emailAddress, ReportID reportID) {
    return network.execute(() -> delegate.sendReport(emailAddress, reportID));
  }
}
