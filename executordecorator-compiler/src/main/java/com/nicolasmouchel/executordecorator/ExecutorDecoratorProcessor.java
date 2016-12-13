package com.nicolasmouchel.executordecorator;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class ExecutorDecoratorProcessor extends AbstractProcessor {
    public static final String TAG = ExecutorDecorator.class.getSimpleName();
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (final Element annotatedElement : roundEnv.getElementsAnnotatedWith(ExecutorDecorator.class)) {
            if (!ElementKind.METHOD.equals(annotatedElement.getKind())) {
                error(annotatedElement, "Only method can be annotated with %s", TAG);
                return true;
            }
            final ExecutableElement element = (ExecutableElement) annotatedElement;
            if (element.getReturnType().getKind().equals(TypeKind.VOID)) {
                error(annotatedElement, "Method that return void can not be annotated with %s", TAG);
                return true;
            }
            final Element definition = typeUtils.asElement(element.getReturnType());
            if (!definition.getKind().isInterface()) {
                error(annotatedElement, "%s can only be used in interface", TAG);
                return true;
            }

            final ExecutorDecorator annotation = annotatedElement.getAnnotation(ExecutorDecorator.class);

            final ExecutorDecoratorClassGenerator classGenerator = new ExecutorDecoratorClassGenerator(
                    elementUtils.getAllMembers((TypeElement) definition), definition, annotation);

            final TypeSpec typeSpec = classGenerator.generate();
            final PackageElement pkg = elementUtils.getPackageOf(annotatedElement);

            final JavaFile javaFile = JavaFile
                    .builder(pkg.getQualifiedName().toString(), typeSpec)
                    .indent("    ")
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                error(annotatedElement, e.getMessage());
            }
        }
        return false;
    }


    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

}
