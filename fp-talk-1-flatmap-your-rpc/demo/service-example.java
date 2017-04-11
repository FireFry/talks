


public Report transfer(AccountID senderID, Password senderPassword, AccountID receiverID, Credits credits) {
    boolean senderAuthorized = authorize(senderID, senderPassword); // <- IO
    if (!senderAuthorized) {
      throw new NotAuthorizedExeption();
    }
    Account sender = getAccount(senderID); // <- IO?
    Account receiver = getAccount(receiverID); // <- IO?
    InvoiceID invoiceID = billing.transfer(sender.brandID(), receiver.brandID(), credits); // < IO
    Report report = generateBillingReport(invoiceID); // CPU
    send(sender.email(), report); // IO
    return report;
}


// Before:
Invoice transfer(BrandID sender, BrandID receiver, Credits amount);


// After:
Future<Invoice> transfer(BrandID sender, BrandID receiver, Credits amount);




// Berofe:
InvoiceID invoiceID = billing.transfer(sender.brandID(), receiver.brandID(), credits);


// After:
billing.transfer(sender.brandID(), receiver.brandID(), credits) .flatMap(invoiceID ->


// Scala version:
invoideID <- billing.transfer(sender.brandID(), receiver.brandID(), credits)

