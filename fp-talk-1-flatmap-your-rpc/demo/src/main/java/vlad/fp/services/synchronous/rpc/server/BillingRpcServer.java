package vlad.fp.services.synchronous.rpc.server;

import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.synchronous.api.BillingService;
import vlad.fp.services.synchronous.rpc.RpcUtils;

import java.util.concurrent.ExecutorService;

public class BillingRpcServer implements BillingService {
  private final ExecutorService executor;
  private final BillingService delegate;

  public BillingRpcServer(ExecutorService executor, BillingService delegate) {
    this.executor = executor;
    this.delegate = delegate;
  }

  @Override
  public InvoiceID transfer(BrandID sender, BrandID receiver, Credits credits) {
    return RpcUtils.execute(executor, () -> delegate.transfer(sender, receiver, credits));
  }

  @Override
  public Invoice getInvoice(InvoiceID invoiceID) {
    return RpcUtils.execute(executor, () -> delegate.getInvoice(invoiceID));
  }
}
