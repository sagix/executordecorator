package immutable;

import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

public final class VoidMethodDecorator implements VoidMethodModule.VoidMethod {
    private final Executor executor;

    private final VoidMethodModule.VoidMethod decorated;

    public VoidMethodDecorator(Executor executor, VoidMethodModule.VoidMethod decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public void method() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.method();
            }
        });
    }
}