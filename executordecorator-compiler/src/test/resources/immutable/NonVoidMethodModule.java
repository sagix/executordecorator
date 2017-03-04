package immutable;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class NonVoidMethodModule {
    interface NonVoidMethod{
        int method();
    }

    @ImmutableExecutorDecorator
    public NonVoidMethod produceNonVoidMethod(){
        return null;
    }
}