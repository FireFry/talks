package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;

public interface FrontendService {

  void transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits);

}
