package vlad.fp.services.asynchronous;

import static vlad.fp.lib.Utils.voidF;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.asynchronous.rpc.client.AccountRpcClientF;
import vlad.fp.services.asynchronous.rpc.client.BillingRpcClientF;
import vlad.fp.services.asynchronous.rpc.client.EmailRpcClientF;
import vlad.fp.services.asynchronous.rpc.client.FrontendRpcClientF;
import vlad.fp.services.asynchronous.rpc.client.ReportRpcClientF;
import vlad.fp.services.asynchronous.rpc.client.SecurityRpcClientF;
import vlad.fp.services.asynchronous.rpc.server.AccountRpcServerF;
import vlad.fp.services.asynchronous.rpc.server.BillingRpcServerF;
import vlad.fp.services.asynchronous.rpc.server.EmailRpcServerF;
import vlad.fp.services.asynchronous.rpc.server.FrontendRpcServerF;
import vlad.fp.services.asynchronous.rpc.server.ReportRpcServerF;
import vlad.fp.services.asynchronous.rpc.server.SecurityRpcServerF;
import vlad.fp.services.asynchronous.server.AccountServerF;
import vlad.fp.services.asynchronous.server.BillingServerF;
import vlad.fp.services.asynchronous.server.EmailServerF;
import vlad.fp.services.asynchronous.server.FrontendServerF;
import vlad.fp.services.asynchronous.server.ReportsServerF;
import vlad.fp.services.asynchronous.server.SecurityServerF;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;
import vlad.fp.services.utils.Generator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncTest {

  public static void main(String[] args) throws InterruptedException {
    AccountServerF accountServerF = new AccountServerF();
    AccountRpcServerF accountRpcServerF = new AccountRpcServerF(createExecutor(), accountServerF);
    AccountRpcClientF accountRpcClientF = new AccountRpcClientF(createNetwork(), accountRpcServerF);

    BillingServiceF billingServiceF = new BillingServerF();
    BillingRpcServerF billingRpcServerF = new BillingRpcServerF(createExecutor(), billingServiceF);
    BillingRpcClientF billingRpcClientF = new BillingRpcClientF(createNetwork(), billingRpcServerF);

    ReportsServerF reportsServerF = new ReportsServerF(billingRpcClientF);
    ReportRpcServerF reportRpcServerF = new ReportRpcServerF(createExecutor(), reportsServerF);
    ReportRpcClientF reportRpcClientF = new ReportRpcClientF(createNetwork(), reportRpcServerF);

    EmailServerF emailServerF = new EmailServerF(reportsServerF);
    EmailRpcServerF emailRpcServerF = new EmailRpcServerF(createExecutor(), emailServerF);
    EmailRpcClientF emailRpcClientF = new EmailRpcClientF(createNetwork(), emailRpcServerF);

    SecurityServerF securityServerF = new SecurityServerF();
    SecurityRpcServerF securityRpcServerF = new SecurityRpcServerF(createExecutor(), securityServerF);
    SecurityRpcClientF securityRpiClientF = new SecurityRpcClientF(createNetwork(), securityRpcServerF);

    FrontendServerF frontendServerF = new FrontendServerF(
        accountRpcClientF, securityRpiClientF, billingRpcClientF, reportRpcClientF, emailRpcClientF);
    FrontendRpcServerF frontendRpcServerF = new FrontendRpcServerF(createExecutor(), frontendServerF);
    FrontendRpcClientF frontendRpcClientF = new FrontendRpcClientF(createNetwork(), frontendRpcServerF);

    int nThreads = 8;
    long startAt = System.currentTimeMillis();
    Random random = new Random();
    ExecutorService testExecutor = Executors.newFixedThreadPool(nThreads);
    List<Job> jobs = generateJobs();
    AtomicInteger completed = new AtomicInteger();
    for (int i = 0; i < nThreads; i++) {
      testExecutor.execute(() -> {
        while (true) {
          Job job = jobs.get(random.nextInt(jobs.size()));
          frontendRpcClientF.transfer(job.senderID(), job.senderPassword(), job.receiverID(), job.credits())
              .runAsync(e -> e.match(
                  voidF(Throwable::printStackTrace),
                  voidF(r -> completed.incrementAndGet()))
              );
        }
      });
    }

    while (true) {
      Thread.sleep(1000);
      long secondsPassed = (System.currentTimeMillis() - startAt) / 1000;
      System.out.println("Throughput: " + (completed.get() / secondsPassed) + " req/sec");
    }
  }

  private static ImmutableList<Job> generateJobs() {
    Generator gen = new Generator(new Random());
    Builder<Job> builder = ImmutableList.builder();
    for (int i = 0; i < 10000; i++) {
      builder.add(new Job(new AccountID(gen.genString(10)),
          new Password(gen.genString(10)),
          new AccountID(gen.genString(10)),
          new Credits(gen.genInt(10000))));
    }
    return builder.build();
  }

  private static ExecutorService createExecutor() {
    return Executors.newFixedThreadPool(8);
  }

  private static NetworkF createNetwork() {
    return new NetworkF(new Random(), Executors.newScheduledThreadPool(8), 1, 10);
  }

  private static final class Job {
    private final AccountID senderID;
    private final Password senderPassword;
    private final AccountID receiverID;
    private final Credits credits;

    private Job(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
      this.senderID = senderID;
      this.senderPassword = senderPassword;
      this.receiverID = receiverID;
      this.credits = credits;
    }

    public AccountID senderID() {
      return senderID;
    }

    public Password senderPassword() {
      return senderPassword;
    }

    public AccountID receiverID() {
      return receiverID;
    }

    public Credits credits() {
      return credits;
    }
  }
}
