package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.BillingServiceF;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BillingServerF implements BillingServiceF {
  private final ConcurrentMap<InvoiceID, Invoice> invoices = new ConcurrentHashMap<>();

  @Override
  public Task<InvoiceID> transfer(BrandID sender, BrandID receiver, Credits credits) {
    InvoiceID invoiceID = new InvoiceID(UUID.randomUUID().toString());
    invoices.put(invoiceID, new Invoice(invoiceID, sender, receiver, credits));
    return Task.now(invoiceID);
  }

  @Override
  public Task<Invoice> getInvoice(InvoiceID invoiceID) {
    return Task.now(invoices.get(invoiceID));
  }
}
