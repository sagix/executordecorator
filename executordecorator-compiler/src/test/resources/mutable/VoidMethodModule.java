package mutable;

import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.MutableDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @MutableExecutorDecorator
    public MutableDecorator<VoidMethod> produceMutableVoidMethod() {
        return null;
    }

    public VoidMethod produceVoidMethod(MutableDecorator<VoidMethod> mutable){
        return mutable.asDecorated();
    }
}