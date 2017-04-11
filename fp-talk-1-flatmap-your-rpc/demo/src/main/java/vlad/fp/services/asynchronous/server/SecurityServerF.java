package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.SecurityServiceF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;

public class SecurityServerF implements SecurityServiceF {

  @Override
  public Task<Boolean> authorize(AccountID accountID, Password password) {
    return Task.now(true); // yolo
  }

}
