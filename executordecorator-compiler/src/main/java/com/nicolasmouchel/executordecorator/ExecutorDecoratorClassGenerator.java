package com.nicolasmouchel.executordecorator;

import com.squareup.javapoet.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

class ExecutorDecoratorClassGenerator {
    private final Element definition;
    private Generator generator;
    private final List<? extends Element> members;

    ExecutorDecoratorClassGenerator(List<? extends Element> members,
                                    Element definition,
                                    Generator generator) {
        this.members = members;
        this.definition = definition;
        this.generator = generator;
    }

    TypeSpec generate(Element annotatedElement) {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        methodSpecList.addAll(generator.generateMethodSpecList());
        methodSpecList.addAll(generateMethods(ElementFilter.methodsIn(members)));
        return generateClassSpec(methodSpecList, annotatedElement);
    }

    private List<MethodSpec> generateMethods(List<ExecutableElement> executableElements) {
        final List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        for (ExecutableElement method : executableElements) {
            if (!method.getEnclosingElement().getSimpleName().toString()
                    .equals(Object.class.getSimpleName())) {
                if (method.getKind().equals(ElementKind.METHOD)
                        && method.getReturnType().getKind().equals(TypeKind.VOID)) {
                    methodSpecList.add(new RunnableMethodGenerator(method).generate());
                } else {
                    methodSpecList.add(new StraightMethodGenerator(method).generate());
                }
            }
        }
        return methodSpecList;
    }

    private TypeSpec generateClassSpec(List<MethodSpec> methodSpecList, Element annotatedElement) {


        return TypeSpec.classBuilder(definition.getSimpleName() + "Decorator")
                .addSuperinterfaces(generator.generateSuperinterfaces())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(Executor.class, "executor", Modifier.PRIVATE, Modifier.FINAL)
                .addFields(generator.generateFields())
                .addMethods(methodSpecList)
                .addJavadoc("Class generated from annotation {@link $T} in class: {@link $N#$L}\n",
                        this.generator.annotation(),
                        getNameEnclosingName(annotatedElement),
                        annotatedElement.getSimpleName()
                )
                .build();
    }

    private Object getNameEnclosingName(Element annotatedElement) {
        Element enclosingElement = annotatedElement.getEnclosingElement();
        if (enclosingElement instanceof TypeElement) {
            return ((TypeElement) enclosingElement).getQualifiedName();
        } else {
            return enclosingElement.getSimpleName();
        }
    }

    private abstract class MethodGenerator {
        private final ExecutableElement method;

        MethodGenerator(ExecutableElement method) {
            this.method = method;
        }

        MethodSpec generate() {
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

            String[] aArr = paramNameList.toArray(new String[paramNameList.size()]);
            final StringBuilder sbStr = new StringBuilder();
            for (int i = 0, il = aArr.length; i < il; i++) {
                if (i > 0)
                    sbStr.append(",");
                sbStr.append(aArr[i]);
            }
            final String params = sbStr.toString();

            return MethodSpec.methodBuilder(name)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(method.getReturnType()))
                    .addParameters(paramList)
                    .addCode(generateBodyMethod(name, params))
                    .build();
        }

        abstract CodeBlock generateBodyMethod(String name, String params);
    }

    private class RunnableMethodGenerator extends MethodGenerator {

        RunnableMethodGenerator(ExecutableElement method) {
            super(method);
        }

        @Override
        CodeBlock generateBodyMethod(String name, String params) {
            final MethodSpec.Builder run = MethodSpec.methodBuilder("run")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class);
            run.addCode(generator.generateCoreMethod(name, params));

            final TypeSpec runnable = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(Runnable.class)
                    .addMethod(run.build())
                    .build();
            return CodeBlock.builder().addStatement("$N.execute($L)", "executor", runnable).build();
        }
    }

    private class StraightMethodGenerator extends MethodGenerator {

        StraightMethodGenerator(ExecutableElement method) {
            super(method);
        }

        @Override
        CodeBlock generateBodyMethod(String name, String params) {
            return CodeBlock.builder().addStatement("return decorated.$N($N)", name, params).build();
        }
    }
}
