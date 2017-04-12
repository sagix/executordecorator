package com.nicolasmouchel.executordecorator.samples;

import java.util.concurrent.Executor;

public class DummyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
