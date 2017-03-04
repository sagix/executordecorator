package mutable;

import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;

final class DummyModule {
    interface Dummy {

    }

    @MutableExecutorDecorator()
    public Dummy produceDummy() {
        return null;
    }
}