# Executor Decorator

Annotation library which create a decorator for interfaces that delegate any actions with a executor

## Installation

With android gradle plugin:
```groovy
provided 'com.nicolasmouchel:executordecorator-annotations:2.0'
annotationProcessor 'com.nicolasmouchel:executordecorator-compiler:2.0'
```
## Usage
Annotate a method that return a interface and a \*Decorator will be generated

## Example
```java
interface MyInterface{
    void method();
}
```
```java 
@ImmutableExecutorDecorator public MyInterface provideMyInterface(){}
```
Will generate:
```java
public final class MyInterfaceDecorator implements MyInterface {
    private final Executor executor;

    private final MyInterface decorated;

    public MyInterfaceDecorator(Executor executor, MyInterface decorated) {
        this.executor = executor;
        this.decorated = decorated;
    }

    @Override
    public void method() {
        executor.execute(new Runnable() {

            @Override()
            public void run() {
                decorated.method();
            }
        });
    }
}
```
For more example, look at the unit tests and the sample project.

## Also
The decorator can be mutable: `@MutableExecutorDecorator` and with a `WeakReference` with `@WeakExecutorDecorator` 
