package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;
import vlad.fp.services.synchronous.api.BillingService;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BillingServer implements BillingService {
  private final ConcurrentMap<InvoiceID, Invoice> invoices = new ConcurrentHashMap<>();

  @Override
  public InvoiceID transfer(BrandID sender, BrandID receiver, Credits credits) {
    InvoiceID invoiceID = new InvoiceID(UUID.randomUUID().toString());
    invoices.put(invoiceID, new Invoice(invoiceID, sender, receiver, credits));
    return invoiceID;
  }

  @Override
  public Invoice getInvoice(InvoiceID invoiceID) {
    return invoices.get(invoiceID);
  }
}
