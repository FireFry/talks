package vlad.fp.services.synchronous;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Password;
import vlad.fp.services.synchronous.api.BillingService;
import vlad.fp.services.synchronous.rpc.Network;
import vlad.fp.services.synchronous.rpc.client.AccountRpcClient;
import vlad.fp.services.synchronous.rpc.client.BillingRpcClient;
import vlad.fp.services.synchronous.rpc.client.EmailRpcClient;
import vlad.fp.services.synchronous.rpc.client.FrontendRpcClient;
import vlad.fp.services.synchronous.rpc.client.ReportRpcClient;
import vlad.fp.services.synchronous.rpc.client.SecurityRpcClient;
import vlad.fp.services.synchronous.rpc.server.AccountRpcServer;
import vlad.fp.services.synchronous.rpc.server.BillingRpcServer;
import vlad.fp.services.synchronous.rpc.server.EmailRpcServer;
import vlad.fp.services.synchronous.rpc.server.FrontendRpcServer;
import vlad.fp.services.synchronous.rpc.server.ReportRpcServer;
import vlad.fp.services.synchronous.rpc.server.SecurityRpcServer;
import vlad.fp.services.synchronous.server.AccountServer;
import vlad.fp.services.synchronous.server.BillingServer;
import vlad.fp.services.synchronous.server.EmailServer;
import vlad.fp.services.synchronous.server.FrontendServer;
import vlad.fp.services.synchronous.server.ReportsServer;
import vlad.fp.services.synchronous.server.SecurityServer;
import vlad.fp.services.utils.Generator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncTest {

  public static void main(String[] args) throws InterruptedException {
    AccountServer accountServer = new AccountServer();
    AccountRpcServer accountRpcServer = new AccountRpcServer(createExecutor(), accountServer);
    AccountRpcClient accountRpcClient = new AccountRpcClient(createNetwork(), accountRpcServer);

    BillingService billingService = new BillingServer();
    BillingRpcServer billingRpcServer = new BillingRpcServer(createExecutor(), billingService);
    BillingRpcClient billingRpcClient = new BillingRpcClient(createNetwork(), billingRpcServer);

    ReportsServer reportsServer = new ReportsServer(billingRpcClient);
    ReportRpcServer reportRpcServer = new ReportRpcServer(createExecutor(), reportsServer);
    ReportRpcClient reportRpcClient = new ReportRpcClient(createNetwork(), reportRpcServer);

    EmailServer emailServer = new EmailServer(reportsServer);
    EmailRpcServer emailRpcServer = new EmailRpcServer(createExecutor(), emailServer);
    EmailRpcClient emailRpcClient = new EmailRpcClient(createNetwork(), emailRpcServer);

    SecurityServer securityServer = new SecurityServer();
    SecurityRpcServer securityRpcServer = new SecurityRpcServer(createExecutor(), securityServer);
    SecurityRpcClient securityRpiClient = new SecurityRpcClient(createNetwork(), securityRpcServer);

    FrontendServer frontendServer = new FrontendServer(
        accountRpcClient, securityRpiClient, billingRpcClient, reportRpcClient, emailRpcClient);
    FrontendRpcServer frontendRpcServer = new FrontendRpcServer(createExecutor(), frontendServer);
    FrontendRpcClient frontendRpcClient = new FrontendRpcClient(createNetwork(), frontendRpcServer);

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
          frontendRpcClient.transfer(job.senderID(), job.senderPassword(), job.receiverID(), job.credits());
          completed.incrementAndGet();
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

  private static Network createNetwork() {
    return new Network(new Random(), Executors.newScheduledThreadPool(8), 1, 10);
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
