package vlad.fp.services.model;

import java.io.Serializable;

public class EmailAddress implements Serializable {
  private final String firstPart;
  private final String secondPart;

  public EmailAddress(String firstPart, String secondPart) {
    this.firstPart = firstPart;
    this.secondPart = secondPart;
  }

  @Override
  public String toString() {
    return firstPart + "@" + secondPart;
  }
}
