package immutable;

import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

import javax.annotation.Generated;

@Generated("com.nicolasmouchel.executordecorator.ExecutorDecoratorProcessor")
public final class VoidMethodWithParameterDecorator implements VoidMethodWithParameterModule.VoidMethodWithParameter {
    private final Executor executor;

    private final VoidMethodWithParameterModule.VoidMethodWithParameter decorated;

    public VoidMethodWithParameterDecorator(Executor executor, VoidMethodWithParameterModule.VoidMethodWithParameter decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public void method(final int i) {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.method(i);
            }
        });
    }
}