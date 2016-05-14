package com.nicolasmouchel.executordecorator;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class ExecutorDecoratorProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ExecutorDecorator.class)) {
            if (!ElementKind.METHOD.equals(annotatedElement.getKind())) {
                error(annotatedElement, "Only method can be annotated with %s", ExecutorDecorator.class.getSimpleName());
                return true;
            }
            final ExecutableElement element = (ExecutableElement) annotatedElement;
            if (element.getReturnType().getKind().equals(TypeKind.VOID)) {
                error(annotatedElement, "Method that return void can not be annotated with %s", ExecutorDecorator.class.getSimpleName());
                return true;
            }
            final Element definition = typeUtils.asElement(element.getReturnType());
            if (!definition.getKind().isInterface()) {
                error(annotatedElement, "%s can only be used in interface", ExecutorDecorator.class.getSimpleName());
                return true;
            }

            final JavaFile javaFile = generateClass(annotatedElement, definition);
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private JavaFile generateClass(Element annotatedElement, Element definition) {
        final TypeElement definition1 = (TypeElement) definition;
        final List<? extends Element> allMembers = elementUtils.getAllMembers(definition1);
        final List<ExecutableElement> executableElements = ElementFilter.methodsIn(allMembers);

        final List<MethodSpec> methodSpecList = generateMethods(executableElements);
        final MethodSpec flux = generateConstructor(definition);
        final TypeSpec typeSpec = generateClassSpec(definition, methodSpecList, flux);
        PackageElement pkg = elementUtils.getPackageOf(annotatedElement);

        return JavaFile.builder(pkg.getQualifiedName().toString(), typeSpec).build();
    }

    private List<MethodSpec> generateMethods(List<ExecutableElement> executableElements) {
        List<MethodSpec> methodSpecList = new ArrayList<MethodSpec>();
        for (ExecutableElement method : executableElements) {
            if (method.getKind().equals(ElementKind.METHOD)
                    && method.getReturnType().getKind().equals(TypeKind.VOID)
                    && mustBeGenerated(method)) {
                methodSpecList.add(generateMethod(method));
            }
        }
        return methodSpecList;
    }

    private MethodSpec generateMethod(ExecutableElement method) {
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

        TypeSpec runnable = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(Runnable.class)
                .addMethod(MethodSpec.methodBuilder("run")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addStatement("decorated.$N($N)", name, strJoin(paramNameList.toArray(new String[paramNameList.size()]), ","))
                        .build())
                .build();
        return MethodSpec.methodBuilder(name)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameters(paramList)
                .addStatement("$N.execute($L)", "executor", runnable)
                .build();
    }

    private MethodSpec generateConstructor(Element definition) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Executor.class, "executor")
                .addParameter(TypeName.get(definition.asType()), "decorated")
                .addStatement("this.$N = $N", "executor", "executor")
                .addStatement("this.$N = $N", "decorated", "decorated")
                .build();
    }

    private TypeSpec generateClassSpec(Element definition, List<MethodSpec> methodSpecList, MethodSpec flux) {
        return TypeSpec.classBuilder(definition.getSimpleName() + "Decorator")
                .addSuperinterface(TypeName.get(definition.asType()))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(Executor.class, "executor", Modifier.PRIVATE, Modifier.FINAL)
                .addField(TypeName.get(definition.asType()), "decorated", Modifier.PRIVATE, Modifier.FINAL)
                .addMethod(flux)
                .addMethods(methodSpecList)
                .build();
    }

    private static List<String> blackList = Arrays.asList("notify", "notifyAll", "wait");

    private boolean mustBeGenerated(ExecutableElement method) {
        return !blackList.contains(method.getSimpleName().toString());
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
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

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(ExecutorDecorator.class.getCanonicalName());
        return annotations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }
}
