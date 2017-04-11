package vlad.fp.services.asynchronous.rpc.client;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.asynchronous.rpc.NetworkF;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;

public class BillingRpcClientF implements BillingServiceF {
  private final NetworkF network;
  private final BillingServiceF delegate;

  public BillingRpcClientF(NetworkF network, BillingServiceF delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public Task<InvoiceID> transfer(BrandID sender, BrandID receiver, Credits credits) {
    return network.execute(() -> delegate.transfer(sender, receiver, credits));
  }

  @Override
  public Task<Invoice> getInvoice(InvoiceID invoiceID) {
    return network.execute(() -> delegate.getInvoice(invoiceID));
  }
}
