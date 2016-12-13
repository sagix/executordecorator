package immutable;

import java.lang.Long;
import com.nicolasmouchel.executordecorator.ExecutorDecorator;

final class VoidMethodWithParametersModule {
    interface VoidMethodWithParameters {
        void method(int i, Long l);
    }

    @ExecutorDecorator
    public VoidMethodWithParameters produceVoidMethodWithParameters() {
        return null;
    }
}