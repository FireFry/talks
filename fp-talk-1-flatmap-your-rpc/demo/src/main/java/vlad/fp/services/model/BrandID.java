package vlad.fp.services.model;

import java.io.Serializable;

public class BrandID implements Serializable {
  private final String text;

  public BrandID(String text) {
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

    BrandID brandID = (BrandID) o;

    return text.equals(brandID.text);
  }

  @Override
  public int hashCode() {
    return text.hashCode();
  }
}
