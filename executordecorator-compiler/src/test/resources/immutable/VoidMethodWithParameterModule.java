package immutable;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class VoidMethodWithParameterModule {
    interface VoidMethodWithParameter{
        void method(int i);
    }

    @ImmutableExecutorDecorator
    public VoidMethodWithParameter produceVoidMethodWithParameter(){
        return null;
    }
}