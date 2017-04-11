package vlad.fp.services.model;

import java.io.Serializable;

public class InvoiceID implements Serializable {
  private final String text;

  public InvoiceID(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    InvoiceID invoiceID = (InvoiceID) o;

    return text.equals(invoiceID.text);
  }

  @Override
  public int hashCode() {
    return text.hashCode();
  }
}
