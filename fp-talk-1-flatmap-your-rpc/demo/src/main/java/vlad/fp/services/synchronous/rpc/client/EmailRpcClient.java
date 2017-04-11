package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.EmailService;
import vlad.fp.services.synchronous.rpc.Network;

public class EmailRpcClient implements EmailService {
  private final Network network;
  private final EmailService delegate;

  public EmailRpcClient(Network network, EmailService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public void sendReport(EmailAddress emailAddress, ReportID reportID) {
    network.run(() -> delegate.sendReport(emailAddress, reportID));
  }
}
