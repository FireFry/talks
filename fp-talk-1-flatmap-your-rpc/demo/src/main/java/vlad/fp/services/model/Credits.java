package vlad.fp.services.model;

import java.io.Serializable;

public class Credits implements Serializable {
  private final int amount;

  public Credits(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "$" + amount;
  }
}
