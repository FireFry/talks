package vlad.fp.services.asynchronous.rpc.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;

import java.util.concurrent.ExecutorService;

public class BillingRpcServerF implements BillingServiceF {
  private final ExecutorService pool;
  private final BillingServiceF delegate;

  public BillingRpcServerF(ExecutorService pool, BillingServiceF delegate) {
    this.pool = pool;
    this.delegate = delegate;
  }

  @Override
  public Task<InvoiceID> transfer(BrandID sender, BrandID receiver, Credits credits) {
    return Task.fork(() -> delegate.transfer(sender, receiver, credits), pool);
  }

  @Override
  public Task<Invoice> getInvoice(InvoiceID invoiceID) {
    return Task.fork(() -> delegate.getInvoice(invoiceID), pool);
  }
}
