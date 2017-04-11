package vlad.fp.services.synchronous.rpc;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Network {
  private final Random random;
  private final ScheduledExecutorService executorService;
  private final int minDelay;
  private final int maxDelay;

  public Network(Random random, ScheduledExecutorService executorService, int minDelay, int maxDelay) {
    this.random = random;
    this.executorService = executorService;
    this.minDelay = minDelay;
    this.maxDelay = maxDelay;
  }
  
  public <T> T execute(Callable<T> callable) {
    return RpcUtils.await(executorService.schedule(callable, randomDelay(), TimeUnit.MILLISECONDS));
  }

  public void run(Runnable runnable) {
    RpcUtils.await(executorService.schedule(runnable, randomDelay(), TimeUnit.MILLISECONDS));
  }

  private int randomDelay() {
    return random.nextInt(maxDelay - minDelay) + minDelay;
  }

  public void shutdown() {
    executorService.shutdown();
  }
}
