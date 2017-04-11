package vlad.fp.services.synchronous.api;

import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.Credits;
import vlad.fp.services.model.Invoice;
import vlad.fp.services.model.InvoiceID;

public interface BillingService {

  InvoiceID transfer(BrandID sender, BrandID receiver, Credits credits);

  Invoice getInvoice(InvoiceID invoiceID);

}
