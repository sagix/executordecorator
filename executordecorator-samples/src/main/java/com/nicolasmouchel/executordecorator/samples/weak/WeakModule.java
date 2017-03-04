package com.nicolasmouchel.executordecorator.samples.weak;

import com.nicolasmouchel.executordecorator.WeakExecutorDecorator;
import com.nicolasmouchel.executordecorator.samples.DummyExecutor;
import com.nicolasmouchel.executordecorator.samples.Task;

class WeakModule {
    @WeakExecutorDecorator
    Task provideTask(final Task task) {
        return new TaskDecorator(new DummyExecutor(), task);
    }
}
