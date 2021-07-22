package mutable;

import com.nicolasmouchel.executordecorator.MutableDecorator;

import java.lang.Override;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.MutableExecutorDecorator} in class: {@link mutable.DummyModule#produceMutableDummy}
 */
public final class DummyDecorator implements DummyModule.Dummy, MutableDecorator<DummyModule.Dummy> {
    private final Executor executor;

    private DummyModule.Dummy decorated;

    public DummyDecorator(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void mutate(final DummyModule.Dummy decorated) {
        this.decorated = decorated;
    }

    @Override
    public DummyModule.Dummy asDecorated() {
        return this;
    }
}