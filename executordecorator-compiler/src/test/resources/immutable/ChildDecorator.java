package immutable;

import java.lang.Override;
import java.lang.Runnable;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator} in class: {@link immutable.InheritanceModule#produceChild}
 */
public final class ChildDecorator implements InheritanceModule.Child {
    private final Executor executor;

    private final InheritanceModule.Child decorated;

    public ChildDecorator(Executor executor, InheritanceModule.Child decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public void mother() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.mother();
            }
        });
    }

    @Override
    public void child() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.child();
            }
        });
    }
}