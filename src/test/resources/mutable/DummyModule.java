package mutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class DummyModule {
    interface Dummy {

    }

    @ExecutorDecorator(mutable = true)
    public Dummy produceDummy() {
        return null;
    }
}