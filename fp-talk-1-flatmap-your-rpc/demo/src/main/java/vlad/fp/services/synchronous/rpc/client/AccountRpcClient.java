package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.synchronous.api.AccountService;
import vlad.fp.services.synchronous.rpc.Network;

public class AccountRpcClient implements AccountService {
  private final Network network;
  private final AccountService delegate;

  public AccountRpcClient(Network network, AccountService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Account getAccount(AccountID accountId) {
    return network.execute(() -> delegate.getAccount(accountId));
  }
}
