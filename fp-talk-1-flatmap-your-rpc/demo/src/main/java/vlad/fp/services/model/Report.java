package vlad.fp.services.model;

import java.io.Serializable;

public class Report implements Serializable {
  private final String text;

  public Report(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
