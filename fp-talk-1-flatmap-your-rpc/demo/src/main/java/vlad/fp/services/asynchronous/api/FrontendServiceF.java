package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;

public interface FrontendServiceF {

  Task<Void> transfer(
      AccountID senderID,
      Password senderPassword,
      AccountID receiverID,
      Credits credits);

}
