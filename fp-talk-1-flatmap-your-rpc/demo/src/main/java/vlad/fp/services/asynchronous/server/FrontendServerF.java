package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.AccountServiceF;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.asynchronous.api.EmailServiceF;
import vlad.fp.services.asynchronous.api.FrontendServiceF;
import vlad.fp.services.asynchronous.api.ReportsServiceF;
import vlad.fp.services.asynchronous.api.SecurityServiceF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;

public class FrontendServerF implements FrontendServiceF {
  private final AccountServiceF accountService;
  private final SecurityServiceF securityService;
  private final BillingServiceF billingService;
  private final ReportsServiceF reportsService;
  private final EmailServiceF emailService;

  public FrontendServerF(
      AccountServiceF accountService,
      SecurityServiceF securityService,
      BillingServiceF billingService,
      ReportsServiceF reportsService,
      EmailServiceF emailService
  ) {
    this.accountService = accountService;
    this.securityService = securityService;
    this.billingService = billingService;
    this.reportsService = reportsService;
    this.emailService = emailService;
  }

  @Override
  public Task<Void> transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) { return
    securityService.authorize(senderID, senderPassword) .flatMap( senderAuthorized -> {
    if (!senderAuthorized) {
      return Task.fail(new RuntimeException("Not authorized"));
    } return
    accountService.getAccount(senderID) .flatMap( sender ->
    accountService.getAccount(receiverID) .flatMap( receiver ->
    billingService.transfer(sender.brandID(), receiver.brandID(), credits) .flatMap( invoiceID ->
    reportsService.generateBillingReport(invoiceID) .flatMap( reportID ->
    emailService.sendReport(sender.emailAddress(), reportID) )))); });
  }
}
