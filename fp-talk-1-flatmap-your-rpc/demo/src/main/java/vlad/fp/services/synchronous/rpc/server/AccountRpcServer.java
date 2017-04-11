package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.synchronous.api.AccountService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class AccountRpcServer implements AccountService {
  private final ExecutorService executor;
  private final AccountService delegate;

  public AccountRpcServer(ExecutorService executor, AccountService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public Account getAccount(AccountID id) {
    return RpcUtils.execute(executor, () -> delegate.getAccount(id));
  }
}
