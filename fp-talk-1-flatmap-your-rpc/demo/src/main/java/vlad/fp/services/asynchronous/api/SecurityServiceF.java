package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;

public interface SecurityServiceF {

  Task<Boolean> authorize(AccountID accountID, Password password);

}
