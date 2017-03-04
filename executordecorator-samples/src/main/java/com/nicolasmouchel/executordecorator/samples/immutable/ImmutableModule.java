package com.nicolasmouchel.executordecorator.samples.immutable;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.samples.DummyExecutor;
import com.nicolasmouchel.executordecorator.samples.Task;

class ImmutableModule {
    @ImmutableExecutorDecorator
    Task provideTask(final Task task) {
        return new TaskDecorator(new DummyExecutor(), task);
    }
}
