package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.FrontendService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class FrontendRpcServer implements FrontendService {
  private final ExecutorService executor;
  private final FrontendService delegate;

  public FrontendRpcServer(ExecutorService executor, FrontendService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public void transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    RpcUtils.run(executor, () -> delegate.transfer(senderID, senderPassword, receiverID, credits));
  }
}
