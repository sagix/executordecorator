package immutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class DummyModule {
    interface Dummy{

    }

    @ExecutorDecorator
    public Dummy produceDummy(){
        return null;
    }
}