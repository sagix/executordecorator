package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

class ExecutorDecoratorClassGenerator {
    private final Element definition;
    private final List<? extends Element> members;
    private final ExecutorDecorator annotation;

    public ExecutorDecoratorClassGenerator(List<? extends Element> members,
                                           Element definition,
                                           ExecutorDecorator annotation) {
        this.members = members;
        this.definition = definition;
        this.annotation = annotation;
    }

    public TypeSpec generate() {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        if (annotation.mutable()) {
            methodSpecList.add(generateMutableMethod());
        }
        methodSpecList.addAll(generateMethods(ElementFilter.methodsIn(members)));
        final MethodSpec constructorSpec = generateConstructorSpec();
        return generateClassSpec(methodSpecList, constructorSpec);
    }

    private List<MethodSpec> generateMethods(List<ExecutableElement> executableElements) {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        for (ExecutableElement method : executableElements) {
            if (mustBeGenerated(method)) {
                if (method.getKind().equals(ElementKind.METHOD)
                        && method.getReturnType().getKind().equals(TypeKind.VOID)) {
                    methodSpecList.add(generateMethod(method, true));
                } else {
                    methodSpecList.add(generateMethod(method, false));
                }
            }
        }
        return methodSpecList;
    }

    private MethodSpec generateMethod(ExecutableElement method, boolean useRunnable) {
        final String name = method.getSimpleName().toString();

        List<ParameterSpec> paramList = new ArrayList<ParameterSpec>();
        List<String> paramNameList = new ArrayList<String>();
        for (VariableElement variableElement : method.getParameters()) {

            final String name1 = variableElement.getSimpleName().toString();
            paramNameList.add(name1);
            final TypeName type = TypeName.get(variableElement.asType());
            final Modifier[] modifiers = variableElement.getModifiers()
                    .toArray(new Modifier[variableElement.getModifiers().size()]);
            paramList.add(ParameterSpec
                    .builder(type, name1, modifiers)
                    .addModifiers(Modifier.FINAL)
                    .build()
            );
        }

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(name)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(method.getReturnType()))
                .addParameters(paramList);
        if (annotation.mutable()) {
            builder.addCode(CodeBlock.builder()
                    .beginControlFlow("if( decorated == null )")
                    .addStatement("return")
                    .endControlFlow()
                    .build());
        }
        final String params = strJoin(paramNameList.toArray(new String[paramNameList.size()]), ",");
        if (useRunnable) {
            final TypeSpec runnable = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(Runnable.class)
                    .addMethod(MethodSpec.methodBuilder("run")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(void.class)
                            .addStatement("decorated.$N($N)", name, params)
                            .build())
                    .build();
            builder.addStatement("$N.execute($L)", "executor", runnable);
        } else {
            builder.addStatement("return decorated.$N($N)", name, params);
        }

        return builder
                .build();
    }

    private MethodSpec generateMutableMethod() {
        return MethodSpec.methodBuilder("set" + definition.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec
                        .builder(TypeName.get(definition.asType()), "decorated")
                        .addModifiers(Modifier.FINAL)
                        .build())
                .addStatement("this.decorated = decorated")
                .build();
    }

    private MethodSpec generateConstructorSpec() {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Executor.class, "executor").addStatement("this.$N = $N", "executor", "executor");
        if (!annotation.mutable()) {
            builder.addParameter(TypeName.get(definition.asType()), "decorated")
                    .addStatement("this.$N = $N", "decorated", "decorated");
        }
        return builder.build();
    }

    private TypeSpec generateClassSpec(
            List<MethodSpec> methodSpecList,
            MethodSpec flux) {
        final boolean mutable = annotation.mutable();
        final Modifier[] modifiers = new Modifier[mutable ? 1 : 2];
        modifiers[0] = Modifier.PRIVATE;
        if (!mutable) {
            modifiers[1] = Modifier.FINAL;
        }

        return TypeSpec.classBuilder(definition.getSimpleName() + "Decorator")
                .addSuperinterface(TypeName.get(definition.asType()))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(Executor.class, "executor", Modifier.PRIVATE, Modifier.FINAL)
                .addField(TypeName.get(definition.asType()), "decorated", modifiers)
                .addMethod(flux)
                .addMethods(methodSpecList)
                .build();
    }

    private static List<String> blackList = Arrays.asList("hashCode", "equals", "getClass", "toString", "notify", "notifyAll", "wait");

    private static boolean mustBeGenerated(ExecutableElement method) {
        return !blackList.contains(method.getSimpleName().toString());
    }

    private static String strJoin(String[] aArr, String sSep) {
        final StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = aArr.length; i < il; i++) {
            if (i > 0)
                sbStr.append(sSep);
            sbStr.append(aArr[i]);
        }
        return sbStr.toString();
    }

}
