package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.SecurityServiceF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;

import java.util.concurrent.ExecutorService;

public class SecurityRpcServerF implements SecurityServiceF {
  private final ExecutorService pool;
  private final SecurityServiceF delegate;

  public SecurityRpcServerF(ExecutorService pool, SecurityServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<Boolean> authorize(AccountID accountID, Password password) {
    return Task.fork(() -> delegate.authorize(accountID, password), pool);
  }
}
