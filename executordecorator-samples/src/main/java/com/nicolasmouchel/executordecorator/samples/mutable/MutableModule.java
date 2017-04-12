package com.nicolasmouchel.executordecorator.samples.mutable;

import com.nicolasmouchel.executordecorator.MutableDecorator;
import com.nicolasmouchel.executordecorator.MutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.samples.DummyExecutor;
import com.nicolasmouchel.executordecorator.samples.Task;

class MutableModule {
    @MutableExecutorDecorator
    MutableDecorator<Task> provideTask() {
        return new TaskDecorator(new DummyExecutor());
    }
}
