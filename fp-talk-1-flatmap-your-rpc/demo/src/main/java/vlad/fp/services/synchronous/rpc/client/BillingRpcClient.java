package vlad.fp.services.synchronous.rpc.client;

import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.synchronous.api.BillingService;
import vlad.fp.services.synchronous.rpc.Network;

public class BillingRpcClient implements BillingService {
  private final Network network;
  private final BillingService delegate;

  public BillingRpcClient(Network network, BillingService delegate) {
    this.network = network;
    this.delegate = delegate;
  }

  @Override
  public InvoiceID transfer(BrandID sender, BrandID receiver, Credits credits) {
    return network.execute(() -> delegate.transfer(sender, receiver, credits));
  }

  @Override
  public Invoice getInvoice(InvoiceID invoiceID) {
    return network.execute(() -> delegate.getInvoice(invoiceID));
  }
}
