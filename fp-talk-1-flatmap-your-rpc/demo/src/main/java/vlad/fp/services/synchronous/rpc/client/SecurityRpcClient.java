package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.SecurityService;
import vlad.fp.services.synchronous.rpc.Network;

public class SecurityRpcClient implements SecurityService {
  private final Network network;
  private final SecurityService delegate;

  public SecurityRpcClient(Network network, SecurityService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public boolean authorize(AccountID accountID, Password password) {
    return network.execute(() -> delegate.authorize(accountID, password));
  }
}
