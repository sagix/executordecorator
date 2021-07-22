package immutable;

import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator} in class: {@link immutable.DummyModule#produceDummy}
 */
public final class DummyDecorator implements DummyModule.Dummy {
    private final Executor executor;

    private final DummyModule.Dummy decorated;

    public DummyDecorator(Executor executor, DummyModule.Dummy decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }
}