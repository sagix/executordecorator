package mutable;

import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

public final class VoidMethodDecorator implements VoidMethodModule.VoidMethod {
    private final Executor executor;

    private VoidMethodModule.VoidMethod decorated;

    public VoidMethodDecorator(Executor executor) {
        this.executor = executor;
    }

    public void setVoidMethod(final VoidMethodModule.VoidMethod decorated) {
        this.decorated = decorated;
    }

    @Override
    public void method() {
        if (decorated == null) {
            return;
        }
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.method();
            }
        });
    }
}