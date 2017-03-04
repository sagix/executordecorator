package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import java.util.List;

public interface Generator {
    Iterable<FieldSpec> generateFields(Element definition);

    CodeBlock generateCoreMethod(String name, String params);

    List<MethodSpec> generateMethodSpecList(Element definition);
}
