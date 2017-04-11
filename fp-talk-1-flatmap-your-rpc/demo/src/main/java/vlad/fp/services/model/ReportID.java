package vlad.fp.services.model;

import java.io.Serializable;

public class ReportID implements Serializable {
  private final String text;

  public ReportID(String text) {
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

    ReportID reportID = (ReportID) o;

    return text.equals(reportID.text);
  }

  @Override
  public int hashCode() {
    return text.hashCode();
  }
}
