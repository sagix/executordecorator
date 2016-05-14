package immutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class VoidMethodWithParameterModule {
    interface VoidMethodWithParameter{
        void method(int i);
    }

    @ExecutorDecorator
    public VoidMethodWithParameter produceVoidMethodWithParameter(){
        return null;
    }
}