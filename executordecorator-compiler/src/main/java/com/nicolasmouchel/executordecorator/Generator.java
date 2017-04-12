package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public interface Generator {
    Iterable<FieldSpec> generateFields();

    CodeBlock generateCoreMethod(String name, String params);

    List<MethodSpec> generateMethodSpecList();

    Iterable<? extends TypeName> generateSuperinterfaces();

    void setRawType(TypeMirror rawType);

    void setDefinition(Element realDefinition);
}
