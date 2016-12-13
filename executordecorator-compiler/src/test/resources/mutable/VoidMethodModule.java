package mutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @ExecutorDecorator(mutable = true)
    public VoidMethod produceVoidMethod(){
        return null;
    }
}