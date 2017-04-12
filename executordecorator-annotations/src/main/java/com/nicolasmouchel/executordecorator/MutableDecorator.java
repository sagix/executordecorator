package com.nicolasmouchel.executordecorator;

public interface MutableDecorator<D> {

    void mutate(D decorated);

    D asDecorated();
}
