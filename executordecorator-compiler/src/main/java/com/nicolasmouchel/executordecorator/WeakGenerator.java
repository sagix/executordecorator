package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;


public class WeakGenerator extends AbstractGenerator {
    @Override
    public Iterable<FieldSpec> generateFields() {
        final ParameterizedTypeName typeVariableName = ParameterizedTypeName.get(
                ClassName.get(WeakReference.class),
                TypeName.get(definition.asType())
        );
        return Collections.singletonList(
                FieldSpec.builder(
                        typeVariableName,
                        "weakReference",
                        Modifier.PRIVATE, Modifier.FINAL)
                        .build()
        );
    }

    @Override
    public CodeBlock generateCoreMethod(String name, String params) {
        return CodeBlock.builder()
                .addStatement("final $T decorated = weakReference.get()", TypeName.get(definition.asType()))
                .beginControlFlow("if (decorated != null)")
                .addStatement("decorated.$N($N)", name, params)
                .endControlFlow().build();
    }

    @Override
    public List<MethodSpec> generateMethodSpecList() {
        return Collections.singletonList(
                MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Executor.class, "executor")
                        .addStatement("this.$N = $N", "executor", "executor")
                        .addParameter(TypeName.get(definition.asType()), "decorated")
                        .addStatement("this.$N = new WeakReference<$T>($N)", "weakReference", definition.asType(), "decorated")
                        .build()
        );
    }

    @Override
    public Iterable<? extends TypeName> generateSuperinterfaces() {
        return Collections.singletonList(TypeName.get(definition.asType()));
    }
}
