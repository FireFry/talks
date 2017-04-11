package vlad.fp.lib;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Trampoline<T> {

  public static <T> Trampoline<T> done(T value) {
    return Done(value);
  }

  public static <T> Trampoline<T> suspend(Supplier<Trampoline<T>> thunk) {
    return Suspend(thunk);
  }

  public static <T> Trampoline<T> delay(Supplier<T> supplier) {
    return suspend(() -> done(supplier.get()));
  }

  private enum Type {
    DONE,
    SUSPEND,
    BIND_SUSPEND,
  }

  private Trampoline() {
    // private constructor
  }

  protected abstract Type getType();

  private <S, R> R matchT(
      Function<Done<T>, R> doneCase,
      Function<Suspend<T>, R> suspendCase,
      Function<BindSuspend<S, T>, R> bindSuspendCase
  ) {
    switch (getType()) {
      case DONE:
        return doneCase.apply(asDone());
      case SUSPEND:
        return suspendCase.apply(asSuspend());
      case BIND_SUSPEND:
        return bindSuspendCase.apply(asBindSuspend());
      default:
        throw new AssertionError();
    }
  }

  public <R> Trampoline<R> flatMap(Function<T, Trampoline<R>> f) {
    return matchT(
        done -> BindSuspend(done, f),
        suspend -> BindSuspend(suspend, f),
        bindSuspend -> BindSuspend(bindSuspend.thunk, s -> bindSuspend.f.apply(s).flatMap(f))
    );
  }

  public <R> Trampoline<R> map(Function<T, R> f) {
    return flatMap(t -> Done(f.apply(t)));
  }

  public T run() {
    return Tailrec.run(resume(), x -> x.match(
        left -> Tailrec.next(left.get().resume()),
        Tailrec::finish
    ));
  }

  private Either<Supplier<Trampoline<T>>, T> resume() {
    return Tailrec.run(this, x -> x.matchT(
        done -> Tailrec.finish(Either.right(done.value)),
        suspend -> Tailrec.finish(Either.left(suspend.thunk)),
        bindSuspend -> bindSuspend.thunk.matchT(
            doneA -> Tailrec.next(bindSuspend.f.apply(doneA.value)),
            suspendA -> Tailrec.finish(Either.left(
                () -> BindSuspend(suspendA.thunk.get(), bindSuspend.f))),
            bindSuspendA -> Tailrec.next(BindSuspend(bindSuspendA.thunk,
                s -> BindSuspend(bindSuspendA.f.apply(s), bindSuspend.f)))
        )
    ));
  }

  private static final class Done<T> extends Trampoline<T> {
    private final T value;

    private Done(T value) {
      this.value = value;
    }

    @Override
    protected Type getType() {
      return Type.DONE;
    }

    @Override
    protected Done<T> asDone() {
      return this;
    }
  }

  private static <T> Done<T> Done(T value) {
    return new Done<>(value);
  }

  protected Done<T> asDone() {
    throw new AssertionError();
  }

  private static final class Suspend<T> extends Trampoline<T> {
    private final Supplier<Trampoline<T>> thunk;

    private Suspend(Supplier<Trampoline<T>> thunk) {
      this.thunk = thunk;
    }

    @Override
    protected Type getType() {
      return Type.SUSPEND;
    }

    @Override
    protected Suspend<T> asSuspend() {
      return this;
    }
  }

  private static <T> Suspend<T> Suspend(Supplier<Trampoline<T>> thunk) {
    return new Suspend<>(thunk);
  }

  protected Suspend<T> asSuspend() {
    throw new AssertionError();
  }

  private static final class BindSuspend<S, T> extends Trampoline<T> {
    private final Trampoline<S> thunk;
    private final Function<S, Trampoline<T>> f;

    private BindSuspend(Trampoline<S> thunk, Function<S, Trampoline<T>> f) {
      this.thunk = thunk;
      this.f = f;
    }

    @Override
    protected Type getType() {
      return Type.BIND_SUSPEND;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BindSuspend<S, T> asBindSuspend() {
      return this;
    }
  }

  private static <S, T> BindSuspend<S, T> BindSuspend(
      Trampoline<S> thunk,
      Function<S, Trampoline<T>> f
  ) {
    return new BindSuspend<>(thunk, f);
  }

  protected <S> BindSuspend<S, T> asBindSuspend() {
    throw new AssertionError();
  }
}
