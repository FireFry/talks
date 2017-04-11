package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;

public interface EmailService {

  void sendReport(EmailAddress emailAddress, ReportID reportID);

}
