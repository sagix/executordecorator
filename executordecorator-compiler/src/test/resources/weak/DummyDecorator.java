package weak;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

import javax.annotation.Generated;

@Generated("com.nicolasmouchel.executordecorator.ExecutorDecoratorProcessor")
public final class DummyDecorator implements DummyModule.Dummy {
    private final Executor executor;

    private final WeakReference<DummyModule.Dummy> weakReference;

    public DummyDecorator(Executor executor, DummyModule.Dummy decorated) {
        this.executor = executor;
        this.weakReference = new WeakReference<DummyModule.Dummy>(decorated);
    }
}