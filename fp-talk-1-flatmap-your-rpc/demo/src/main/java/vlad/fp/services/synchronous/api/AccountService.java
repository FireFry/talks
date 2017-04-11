package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;

public interface AccountService {

  Account getAccount(AccountID id);

}
