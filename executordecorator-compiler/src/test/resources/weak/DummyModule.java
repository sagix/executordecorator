package weak;

import com.nicolasmouchel.executordecorator.WeakExecutorDecorator;

final class DummyModule {
    interface Dummy {

    }

    @WeakExecutorDecorator
    public Dummy produceDummy() {
        return null;
    }
}