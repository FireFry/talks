package vlad.fp.services.model;

import java.io.Serializable;

public class Account implements Serializable {
  private final AccountID id;
  private final BrandID brandID;
  private final EmailAddress emailAddress;

  public Account(AccountID id, BrandID brandID, EmailAddress emailAddress) {
    this.id = id;
    this.brandID = brandID;
    this.emailAddress = emailAddress;
  }

  public AccountID id() {
    return id;
  }

  public BrandID brandID() {
    return brandID;
  }

  public EmailAddress emailAddress() {
    return emailAddress;
  }
}
