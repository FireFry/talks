package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.model.Report;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.EmailService;
import vlad.fp.services.synchronous.api.ReportsService;

import java.util.logging.Logger;

public class EmailServer implements EmailService {
  private final ReportsService reportsService;

  public EmailServer(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @Override
  public void sendReport(EmailAddress emailAddress, ReportID reportID) {
    reportsService.getReport(reportID);
  }
}
