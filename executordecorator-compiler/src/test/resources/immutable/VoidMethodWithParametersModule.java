package immutable;

import java.lang.Long;
import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class VoidMethodWithParametersModule {
    interface VoidMethodWithParameters {
        void method(int i, Long l);
    }

    @ImmutableExecutorDecorator
    public VoidMethodWithParameters produceVoidMethodWithParameters() {
        return null;
    }
}