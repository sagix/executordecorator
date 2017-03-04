package com.nicolasmouchel.executordecorator;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

public abstract class AbstractGenerator implements Generator {

    TypeMirror rawType;
    Element definition;

    @Override
    public void setRawType(TypeMirror rawType) {
        this.rawType = rawType;
    }

    @Override
    public void setDefinition(Element definition) {
        this.definition = definition;
    }
}
