package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.model.Password;
import vlad.fp.services.model.ReportID;
import vlad.fp.services.synchronous.api.AccountService;
import vlad.fp.services.synchronous.api.BillingService;
import vlad.fp.services.synchronous.api.EmailService;
import vlad.fp.services.synchronous.api.FrontendService;
import vlad.fp.services.synchronous.api.ReportsService;
import vlad.fp.services.synchronous.api.SecurityService;

public class FrontendServer implements FrontendService {
  private final AccountService accountService;
  private final SecurityService securityService;
  private final BillingService billingService;
  private final ReportsService reportsService;
  private final EmailService emailService;

  public FrontendServer(
      AccountService accountService,
      SecurityService securityService,
      BillingService billingService,
      ReportsService reportsService,
      EmailService emailService
  ) {
    this.accountService = accountService;
    this.securityService = securityService;
    this.billingService = billingService;
    this.reportsService = reportsService;
    this.emailService = emailService;
  }

  @Override
  public void transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    boolean senderAuthorized = securityService.authorize(senderID, senderPassword);
    if (!senderAuthorized) {
      throw new RuntimeException("Not authorized");
    }
    Account sender = accountService.getAccount(senderID);
    Account receiver = accountService.getAccount(receiverID);
    InvoiceID invoiceID = billingService.transfer(sender.brandID(), receiver.brandID(), credits);
    ReportID reportID = reportsService.generateBillingReport(invoiceID);
    emailService.sendReport(sender.emailAddress(), reportID);
  }
}
