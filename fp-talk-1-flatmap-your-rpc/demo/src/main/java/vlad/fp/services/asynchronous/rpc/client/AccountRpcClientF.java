package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.AccountServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;

public class AccountRpcClientF implements AccountServiceF {
  private final NetworkF network;
  private final AccountServiceF delegate;

  public AccountRpcClientF(NetworkF network, AccountServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<Account> getAccount(AccountID accountId) {
    return network.execute(() -> delegate.getAccount(accountId));
  }
}
