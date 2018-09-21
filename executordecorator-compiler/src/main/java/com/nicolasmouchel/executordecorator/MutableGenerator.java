package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;


public class MutableGenerator extends AbstractGenerator {
    @Override
    public Class annotation() {
        return MutableExecutorDecorator.class;
    }

    @Override
    public Iterable<FieldSpec> generateFields() {
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
    public List<MethodSpec> generateMethodSpecList() {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        methodSpecList.add(
                MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Executor.class, "executor")
                        .addStatement("this.$N = $N", "executor", "executor")
                        .build()
        );
        methodSpecList.add(
                MethodSpec.methodBuilder("mutate")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec
                                .builder(TypeName.get(definition.asType()), "decorated")
                                .addModifiers(Modifier.FINAL)
                                .build())
                        .addStatement("this.decorated = decorated")
                        .build()
        );
        methodSpecList.add(
                MethodSpec.methodBuilder("asDecorated")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.get(definition.asType()))
                        .addStatement("return $N", "this")
                        .build()
        );
        return methodSpecList;
    }

    @Override
    public Iterable<? extends TypeName> generateSuperinterfaces() {
        final List<TypeName> list = new ArrayList<TypeName>();
        final TypeMirror mirror = definition.asType();
        list.add(TypeName.get(mirror));
        if (mirror.equals(rawType)) {
            list.add(ParameterizedTypeName.get(
                    ClassName.get(MutableDecorator.class),
                    TypeName.get(mirror)
            ));
        } else {
            list.add(TypeName.get(rawType));
        }
        return list;
    }
}
