# Executor Decorator

Annotation library which create a decorator for interfaces that delegate any actions with a executor

## Instalation

With gradle:
```groovy
provided 'com.nicolasmouchel:executordecorator-annotations:1.1'
annotationProcessor 'com.nicolasmouchel:executordecorator-compiler:1.1'
```
## Usage
Annotate a method that return a interface and a \*Decorator will be generated

## Example
```java
interface MyInterface{
    void method();
}

@ExecutorDecorator public MyInterface provideMyInterface(){}
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
For more example, look at the unit tests.

## Also
The decorator can be mutable: `@ExecutorDecorator(mutable = true)`
