package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.FrontendService;
import vlad.fp.services.synchronous.rpc.Network;

public class FrontendRpcClient implements FrontendService {
  private final Network network;
  private final FrontendService delegate;

  public FrontendRpcClient(Network network, FrontendService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public void transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    network.run(() -> delegate.transfer(senderID, senderPassword, receiverID, credits));
  }
}
