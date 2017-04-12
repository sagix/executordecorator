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
 
## Migration from 1.0 to 2.0

`ExecutorDecorator` has been removed and must be replaced:
 * `@ExecutorDecorator` or `@ExecutorDecorator(mutable = false)` should be replaced by `@ImmutableExecutorDecorator`
 * `@ExecutorDecorator(mutable = true)` should be replaced by `@MutableExecutorDecorator`

If a project uses Kotlin with Dagger, a module can not provide generated classes.
So now, `MutableDecorator<T>` can be provided and a `TDecorator` class will be generated, implementing both `T` and `MutableDecorator<T>` interfaces.
A cast of `MutableDecorator<T>` to `T` can be done with method `T asDecorated()`
  

