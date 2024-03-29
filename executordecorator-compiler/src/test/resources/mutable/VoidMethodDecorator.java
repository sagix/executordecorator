package mutable;

import com.nicolasmouchel.executordecorator.MutableDecorator;

import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.MutableExecutorDecorator} in class: {@link mutable.VoidMethodModule#produceMutableVoidMethod}
 */
public final class VoidMethodDecorator implements VoidMethodModule.VoidMethod, MutableDecorator<VoidMethodModule.VoidMethod> {
    private final Executor executor;

    private VoidMethodModule.VoidMethod decorated;

    public VoidMethodDecorator(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void mutate(final VoidMethodModule.VoidMethod decorated) {
        this.decorated = decorated;
    }

    @Override
    public VoidMethodModule.VoidMethod asDecorated() {
        return this;
    }

    @Override
    public void method() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                if (decorated != null) {
                    decorated.method();
                }
            }
        });
    }
}