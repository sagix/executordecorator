package com.nicolasmouchel.executordecorator;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class WeakExecutorDecoratorTest {
    @Test
    public void dummy() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("weak/DummyModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("weak/DummyDecorator.java"));
    }

    @Test
    public void voidMethod() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("weak/VoidMethodModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("weak/VoidMethodDecorator.java"));
    }
}