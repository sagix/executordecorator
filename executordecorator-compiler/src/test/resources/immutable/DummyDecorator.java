package immutable;

import java.util.concurrent.Executor;
import javax.annotation.Generated;

@Generated("com.nicolasmouchel.executordecorator.ExecutorDecoratorProcessor")
public final class DummyDecorator implements DummyModule.Dummy {
    private final Executor executor;

    private final DummyModule.Dummy decorated;

    public DummyDecorator(Executor executor, DummyModule.Dummy decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }
}