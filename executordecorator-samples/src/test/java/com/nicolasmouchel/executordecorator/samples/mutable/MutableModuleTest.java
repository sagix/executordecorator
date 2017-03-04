package com.nicolasmouchel.executordecorator.samples.mutable;

import com.nicolasmouchel.executordecorator.MutableDecorator;
import com.nicolasmouchel.executordecorator.samples.MockTask;
import com.nicolasmouchel.executordecorator.samples.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MutableModuleTest {

    private MutableDecorator<Task> taskMutableDecorator;

    @Before
    public void setUp() {
        taskMutableDecorator = new MutableModule().provideTask();
    }

    @Test
    public void givenANotSetDecorator_WhenInvoke_ShouldDoNothing() {
        taskMutableDecorator.asDecorated().invoke();
    }

    @Test
    public void givenASetDecoratedObject_WhenInvoke_ShouldExecute() {
        final MutableDecorator<Task> taskMutableDecorator = new MutableModule().provideTask();
        taskMutableDecorator.asDecorated().invoke();
        final MockTask task = new MockTask();
        taskMutableDecorator.mutate(task);
        taskMutableDecorator.asDecorated().invoke();

        assertTrue(task.isInvoke);
    }

    @Test
    public void givenASetDecoratedObjectReset_WhenInvoke_ShouldExecute() {
        final MutableDecorator<Task> taskMutableDecorator = new MutableModule().provideTask();
        taskMutableDecorator.asDecorated().invoke();
        final MockTask task = new MockTask();
        taskMutableDecorator.mutate(task);
        taskMutableDecorator.mutate(null);
        taskMutableDecorator.asDecorated().invoke();

        assertFalse(task.isInvoke);
    }


}