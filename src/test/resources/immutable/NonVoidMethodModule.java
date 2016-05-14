package immutable;

import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class NonVoidMethodModule {
    interface NonVoidMethod{
        int method();
    }

    @ExecutorDecorator
    public NonVoidMethod produceNonVoidMethod(){
        return null;
    }
}