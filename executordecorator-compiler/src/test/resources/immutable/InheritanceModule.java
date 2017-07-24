package immutable;

import java.lang.Long;
import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;

final class InheritanceModule {
    interface Mother {
        void mother();
    }

    interface Child extends Mother{
        void child();
    }

    @ImmutableExecutorDecorator
    public Child produceChild() {
        return null;
    }
}