package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.EmailServiceF;
import vlad.fp.services.asynchronous.api.ReportsServiceF;
import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.ReportID;

import java.util.logging.Logger;

public class EmailServerF implements EmailServiceF {
  private final ReportsServiceF reportsService;

  public EmailServerF(ReportsServiceF reportsService) {
    this.reportsService = reportsService;
  }

  @Override
  public Task<Void> sendReport(EmailAddress emailAddress, ReportID reportID) {
    return reportsService.getReport(reportID).map(report -> null);
  }
}
