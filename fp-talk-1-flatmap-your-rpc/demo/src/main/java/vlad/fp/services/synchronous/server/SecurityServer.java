package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.SecurityService;

public class SecurityServer implements SecurityService {

  @Override
  public boolean authorize(AccountID accountID, Password password) {
    return true; // yolo
  }

}
