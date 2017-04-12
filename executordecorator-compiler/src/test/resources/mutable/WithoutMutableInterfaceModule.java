package mutable;

import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;

final class WithoutMutableInterfaceModule {

    interface WithoutMutableInterface {}

    @MutableExecutorDecorator
    WithoutMutableInterface provideWithoutMutableInterface(){
        return null;
    }
}