package weak;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.WeakExecutorDecorator} in class: {@link weak.VoidMethodModule#produceVoidMethod}
 */
public final class VoidMethodDecorator implements VoidMethodModule.VoidMethod {
    private final Executor executor;

    private final WeakReference<VoidMethodModule.VoidMethod> weakReference;

    public VoidMethodDecorator(Executor executor, VoidMethodModule.VoidMethod decorated) {
        this.executor = executor;
        this.weakReference = new WeakReference<VoidMethodModule.VoidMethod>(decorated);
    }

    @Override
    public void method() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                final VoidMethodModule.VoidMethod decorated = weakReference.get();
                if (decorated != null) {
                    decorated.method();
                }
            }
        });
    }
}