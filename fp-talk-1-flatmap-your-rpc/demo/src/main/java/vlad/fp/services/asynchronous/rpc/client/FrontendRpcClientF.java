package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.FrontendServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;

public class FrontendRpcClientF implements FrontendServiceF {
  private final NetworkF network;
  private final FrontendServiceF delegate;

  public FrontendRpcClientF(NetworkF network, FrontendServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<Void> transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    return network.execute(() -> delegate.transfer(senderID, senderPassword, receiverID, credits));
  }
}
