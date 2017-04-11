package vlad.fp.services.model;

import com.google.common.base.Preconditions;

import java.io.Serializable;

public class AccountID implements Serializable {
  private final String text;

  public AccountID(String text) {
    this.text = Preconditions.checkNotNull(text);
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

    AccountID accountID = (AccountID) o;

    return text.equals(accountID.text);
  }

  @Override
  public int hashCode() {
    return text.hashCode();
  }
}
