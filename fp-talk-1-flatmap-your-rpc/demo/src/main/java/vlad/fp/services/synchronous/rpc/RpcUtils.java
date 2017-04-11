package vlad.fp.services.synchronous.rpc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public class RpcUtils {

  public static <T> T execute(ExecutorService executor, Callable<T> callable) {
    return await(executor.submit(callable));
  }

  public static void run(ExecutorService executor, Runnable runnable) {
    execute(executor, () -> { runnable.run(); return null; });
  }

  public static <T> T await(Future<T> future) {
    try {
      return future.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

}
