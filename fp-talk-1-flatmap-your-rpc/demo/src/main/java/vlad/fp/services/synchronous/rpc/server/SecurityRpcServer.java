package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.SecurityService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class SecurityRpcServer implements SecurityService {
  private final ExecutorService executor;
  private final SecurityService delegate;

  public SecurityRpcServer(ExecutorService executor, SecurityService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public boolean authorize(AccountID accountID, Password password) {
    return RpcUtils.execute(executor, () -> delegate.authorize(accountID, password));
  }
}
