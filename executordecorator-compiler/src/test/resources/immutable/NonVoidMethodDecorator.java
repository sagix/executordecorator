package immutable;

import java.lang.Override;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator} in class: {@link immutable.NonVoidMethodModule#produceNonVoidMethod}
 */
public final class NonVoidMethodDecorator implements NonVoidMethodModule.NonVoidMethod {
    private final Executor executor;

    private final NonVoidMethodModule.NonVoidMethod decorated;

    public NonVoidMethodDecorator(Executor executor, NonVoidMethodModule.NonVoidMethod decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public int method() {
        return decorated.method();
    }
}