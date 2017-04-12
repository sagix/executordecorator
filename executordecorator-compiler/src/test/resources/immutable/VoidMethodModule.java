package immutable;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @ImmutableExecutorDecorator
    public VoidMethod produceVoidMethod(){
        return null;
    }
}