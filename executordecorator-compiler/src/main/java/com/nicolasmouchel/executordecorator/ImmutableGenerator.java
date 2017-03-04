package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;


public class ImmutableGenerator implements Generator {
    @Override
    public Iterable<FieldSpec> generateFields(Element definition) {
        return Collections.singletonList(
                FieldSpec.builder(
                        TypeName.get(definition.asType()),
                        "decorated",
                        Modifier.PRIVATE, Modifier.FINAL)
                        .build()
        );
    }

    @Override
    public CodeBlock generateCoreMethod(String name, String params) {
        return CodeBlock.builder().addStatement("decorated.$N($N)", name, params).build();
    }

    @Override
    public List<MethodSpec> generateMethodSpecList(Element definition) {
        return Collections.singletonList(
                MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Executor.class, "executor")
                        .addStatement("this.$N = $N", "executor", "executor")
                        .addParameter(TypeName.get(definition.asType()), "decorated")
                        .addStatement("this.$N = $N", "decorated", "decorated")
                        .build()
        );
    }
}
