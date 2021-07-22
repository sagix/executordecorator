package immutable;

import java.lang.Long;
import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator} in class: {@link immutable.VoidMethodWithParametersModule#produceVoidMethodWithParameters}
 */
public final class VoidMethodWithParametersDecorator implements VoidMethodWithParametersModule.VoidMethodWithParameters {
    private final Executor executor;

    private final VoidMethodWithParametersModule.VoidMethodWithParameters decorated;

    public VoidMethodWithParametersDecorator(Executor executor, VoidMethodWithParametersModule.VoidMethodWithParameters decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public void method(final int i, final Long l) {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.method(i, l);
            }
        });
    }
}