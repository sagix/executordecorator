package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;


public class MutableGenerator implements Generator {
    @Override
    public Iterable<FieldSpec> generateFields(Element definition) {
        return Collections.singletonList(
                FieldSpec.builder(
                        TypeName.get(definition.asType()),
                        "decorated",
                        Modifier.PRIVATE)
                        .build()
        );
    }

    @Override
    public CodeBlock generateCoreMethod(String name, String params) {
        return CodeBlock.builder().beginControlFlow("if (decorated != null)")
                .addStatement("decorated.$N($N)", name, params)
                .endControlFlow().build();
    }

    @Override
    public List<MethodSpec> generateMethodSpecList(Element definition) {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        methodSpecList.add(
                MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Executor.class, "executor").addStatement("this.$N = $N", "executor", "executor")
                        .build()
        );
        methodSpecList.add(
                MethodSpec.methodBuilder("set" + definition.getSimpleName().toString())
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec
                                .builder(TypeName.get(definition.asType()), "decorated")
                                .addModifiers(Modifier.FINAL)
                                .build())
                        .addStatement("this.decorated = decorated")
                        .build()
        );
        return methodSpecList;
    }
}
