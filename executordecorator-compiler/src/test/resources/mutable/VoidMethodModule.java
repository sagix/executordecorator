package mutable;

import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @MutableExecutorDecorator
    public VoidMethod produceVoidMethod(){
        return null;
    }
}