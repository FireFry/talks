package vlad.fp.services.model;

import java.io.Serializable;

public class Password implements Serializable {
  private final String text;

  public Password(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
