package immutable;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class DummyModule {
    interface Dummy{

    }

    @ImmutableExecutorDecorator
    public Dummy produceDummy(){
        return null;
    }
}