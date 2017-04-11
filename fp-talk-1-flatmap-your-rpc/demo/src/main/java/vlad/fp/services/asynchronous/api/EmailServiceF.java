package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;

public interface EmailServiceF {

  Task<Void> sendReport(EmailAddress emailAddress, ReportID reportID);

}
