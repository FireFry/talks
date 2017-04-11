package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;

public interface AccountServiceF {

  Task<Account> getAccount(AccountID accountId);

}
