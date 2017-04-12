package com.nicolasmouchel.executordecorator.samples.immutable;

import com.nicolasmouchel.executordecorator.samples.MockTask;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ImmutableModuleTest {
    @Test(expected = NullPointerException.class)
    public void givenANullDecoratedObject_ShouldThrowNullPointerException() {
        new ImmutableModule().provideTask(null).invoke();
    }

    @Test
    public void givenANonNullDecoratedObject_ShouldExecute() {
        final MockTask task = new MockTask();
        new ImmutableModule().provideTask(task).invoke();

        assertTrue(task.isInvoke);
    }
}