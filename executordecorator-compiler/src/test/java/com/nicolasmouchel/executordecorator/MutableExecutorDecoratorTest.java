package com.nicolasmouchel.executordecorator;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class MutableExecutorDecoratorTest {
    @Test
    public void dummy() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("mutable/DummyModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("mutable/DummyDecorator.java"));
    }

    @Test
    public void voidMethod() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("mutable/VoidMethodModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("mutable/VoidMethodDecorator.java"));
    }

    @Test
    public void withoutMutableInterface() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("mutable/WithoutMutableInterfaceModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("mutable/WithoutMutableInterfaceDecorator.java"));
    }
}