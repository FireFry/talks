package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.FrontendServiceF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;

import java.util.concurrent.ExecutorService;

public class FrontendRpcServerF implements FrontendServiceF {
  private final ExecutorService pool;
  private final FrontendServiceF delegate;

  public FrontendRpcServerF(ExecutorService pool, FrontendServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<Void> transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    return Task.fork(() -> delegate.transfer(senderID, senderPassword, receiverID, credits), pool);
  }
}
