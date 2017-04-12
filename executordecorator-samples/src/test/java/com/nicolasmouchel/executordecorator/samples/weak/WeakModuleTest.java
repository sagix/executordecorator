package com.nicolasmouchel.executordecorator.samples.weak;

import com.nicolasmouchel.executordecorator.samples.MockTask;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WeakModuleTest {
    @Test
    public void givenANullDecoratedObject_WhenInvoke_ShouldDoNothing() {
        new WeakModule().provideTask(null).invoke();
    }

    @Test
    public void givenANonNullDecoratedObject_ShouldExecute() {
        final MockTask task = new MockTask();
        new WeakModule().provideTask(task).invoke();

        assertTrue(task.isInvoke);
    }

}