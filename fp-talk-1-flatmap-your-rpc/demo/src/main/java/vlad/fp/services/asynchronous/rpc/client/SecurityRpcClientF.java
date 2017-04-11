package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.SecurityServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;

public class SecurityRpcClientF implements SecurityServiceF {
  private final NetworkF network;
  private final SecurityServiceF delegate;

  public SecurityRpcClientF(NetworkF network, SecurityServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<Boolean> authorize(AccountID accountID, Password password) {
    return network.execute(() -> delegate.authorize(accountID, password));
  }
}
