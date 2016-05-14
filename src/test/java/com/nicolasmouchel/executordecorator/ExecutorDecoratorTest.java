package com.nicolasmouchel.executordecorator;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class ExecutorDecoratorTest {
    @Test
    public void dummy() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("immutable/DummyModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("immutable/DummyDecorator.java"));
    }

    @Test
    public void voidMethod() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("immutable/VoidMethodModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("immutable/VoidMethodDecorator.java"));
    }

    @Test
    public void voidMethodWithParameter() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("immutable/VoidMethodWithParameterModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("immutable/VoidMethodWithParameterDecorator.java"));
    }

    @Test
    public void voidMethodWithParameters() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("immutable/VoidMethodWithParametersModule.java"))
                .processedWith(new ExecutorDecoratorProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("immutable/VoidMethodWithParametersDecorator.java"));
    }
}