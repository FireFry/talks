package vlad.fp.services.utils;

import java.util.Random;

public class Generator {
  private final Random random;

  public Generator(Random random) {
    this.random = random;
  }

  public String genString(int size) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      builder.append((char) (random.nextInt('z' - 'a') + 'a'));
    }
    return builder.toString();
  }

  public int genInt(int bound) {
    return random.nextInt(bound);
  }
}
