package mutable;

import com.nicolasmouchel.executordecorator.MutableDecorator;

import java.lang.Override;
import java.util.concurrent.Executor;

/**
 * Class generated from annotation {@link com.nicolasmouchel.executordecorator.MutableExecutorDecorator} in class: {@link mutable.WithoutMutableInterfaceModule#provideWithoutMutableInterface}
 */
public final class WithoutMutableInterfaceDecorator implements WithoutMutableInterfaceModule.WithoutMutableInterface, MutableDecorator<WithoutMutableInterfaceModule.WithoutMutableInterface> {
    private final Executor executor;

    private WithoutMutableInterfaceModule.WithoutMutableInterface decorated;

    public WithoutMutableInterfaceDecorator(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void mutate(final WithoutMutableInterfaceModule.WithoutMutableInterface decorated) {
        this.decorated = decorated;
    }

    @Override
    public WithoutMutableInterfaceModule.WithoutMutableInterface asDecorated() {
        return this;
    }
}