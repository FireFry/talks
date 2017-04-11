package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.AccountServiceF;
import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;

import java.util.concurrent.ExecutorService;

public class AccountRpcServerF implements AccountServiceF {
  private final ExecutorService pool;
  private final AccountServiceF delegate;

  public AccountRpcServerF(ExecutorService pool, AccountServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<Account> getAccount(AccountID accountId) {
    return Task.fork(() -> delegate.getAccount(accountId), pool);
  }
}
