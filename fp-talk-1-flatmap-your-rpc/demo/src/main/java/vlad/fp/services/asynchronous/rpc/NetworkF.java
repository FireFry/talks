package vlad.fp.services.asynchronous.rpc;

import vlad.fp.lib.Task;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

public class NetworkF {
  private final Random random;
  private final ScheduledExecutorService executor;
  private final int minDelay;
  private final int maxDelay;

  public NetworkF(Random random, ScheduledExecutorService executor, int minDelay, int maxDelay) {
    this.random = random;
    this.executor = executor;
    this.minDelay = minDelay;
    this.maxDelay = maxDelay;
  }

  public <T> Task<T> execute(Supplier<Task<T>> supplier) {
    return Task.scheduleF(supplier, Duration.ofMillis(randomDelay()), executor);
  }

  private int randomDelay() {
    return random.nextInt(maxDelay - minDelay) + minDelay;
  }
}
