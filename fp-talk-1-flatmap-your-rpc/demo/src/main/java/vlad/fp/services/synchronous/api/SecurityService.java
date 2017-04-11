package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;

public interface SecurityService {

  boolean authorize(AccountID accountID, Password password);

}
