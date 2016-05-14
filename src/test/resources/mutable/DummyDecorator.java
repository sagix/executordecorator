package mutable;

import java.util.concurrent.Executor;

public final class DummyDecorator implements DummyModule.Dummy {
    private final Executor executor;

    private DummyModule.Dummy decorated;

    public DummyDecorator(Executor executor) {
        this.executor = executor;
    }

    public void setDummy(DummyModule.Dummy dummy) {
        this.decorated = dummy;
    }
}