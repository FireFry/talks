package vlad.fp.services.asynchronous.api;

import vlad.fp.lib.Task;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;

public interface BillingServiceF {

  Task<InvoiceID> transfer(BrandID sender, BrandID receiver, Credits credits);

  Task<Invoice> getInvoice(InvoiceID invoiceID);

}
