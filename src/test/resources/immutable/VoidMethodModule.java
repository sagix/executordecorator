package immutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @ExecutorDecorator
    public VoidMethod produceVoidMethod(){
        return null;
    }
}