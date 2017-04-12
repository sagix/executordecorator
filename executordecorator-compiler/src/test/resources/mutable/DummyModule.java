package mutable;

import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.MutableDecorator;

final class DummyModule {
    interface Dummy {

    }

    @MutableExecutorDecorator
    public MutableDecorator<Dummy> produceMutableDummy() {
        return null;
    }

    public Dummy produceDummy(MutableDecorator<Dummy> mutable){
        return mutable.asDecorated();
    }
}