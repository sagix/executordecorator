package weak;

import com.nicolasmouchel.executordecorator.WeakExecutorDecorator;

final class VoidMethodModule {
    interface VoidMethod{
        void method();
    }

    @WeakExecutorDecorator
    public VoidMethod produceVoidMethod() {
        return null;
    }
}