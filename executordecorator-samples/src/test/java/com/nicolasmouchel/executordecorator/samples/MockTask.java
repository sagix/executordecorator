package com.nicolasmouchel.executordecorator.samples;

public class MockTask implements Task {
    public boolean isInvoke;

    @Override
    public void invoke() {
        isInvoke = true;
    }
}
