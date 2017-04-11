package vlad.fp.services.model;

import java.io.Serializable;

public class Invoice implements Serializable {
  private final InvoiceID id;
  private final BrandID sender;
  private final BrandID receiver;
  private final Credits credits;

  public Invoice(InvoiceID id, BrandID sender, BrandID receiver, Credits credits) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.credits = credits;
  }

  public InvoiceID id() {
    return id;
  }

  public BrandID sender() {
    return sender;
  }

  public BrandID report() {
    return receiver;
  }

  public Credits credits() {
    return credits;
  }
}
