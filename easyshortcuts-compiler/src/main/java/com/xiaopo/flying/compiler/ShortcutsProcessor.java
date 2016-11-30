package com.xiaopo.flying.compiler;

import com.google.auto.service.AutoService;
import com.xiaopo.flying.annotation.AppShortcut;
import com.xiaopo.flying.annotation.ShortcutApplication;
import com.xiaopo.flying.compiler.model.Shortcut;
import com.xiaopo.flying.compiler.model.ShortcutClass;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ShortcutsProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    private ShortcutClass mShortcutClass;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        int size = roundEnvironment.getElementsAnnotatedWith(ShortcutApplication.class).size();
        int shortcutSize = roundEnvironment.getElementsAnnotatedWith(AppShortcut.class).size();

        if (size == 0 && shortcutSize == 0) return false;
//        printNote(String.valueOf(size));
        if (size == 0) {
            printError("ERROR:You must add one ShortcutApplication annotation in your activity class!");
        } else if (size > 1) {
            printError("ERROR:The number of ShortcutApplication annotation can not greater than 1!");
        }
//        printNote(String.valueOf(shortcutSize));
        if (shortcutSize > 5) {
            printError("The number of AppShortcut annotation can not greater than 5!");
        }


        for (Element element : roundEnvironment.getElementsAnnotatedWith(ShortcutApplication.class)) {
            if (!isValid(element)) {
                return false;
            }
            parseShortcutApplication(element);
        }


        for (Element element : roundEnvironment.getElementsAnnotatedWith(AppShortcut.class)) {
            if (!isValid(element)) {
                return false;
            }

            parseShortcut(element);
        }

        try {
            mShortcutClass.generateCode().writeTo(mFiler);
        } catch (IOException e) {
            printError(e.getMessage());
        }

        return true;
    }

    private void parseShortcutApplication(Element element) {
        TypeElement typeElement = (TypeElement) element;
        mShortcutClass = new ShortcutClass(typeElement, mElementUtils);
    }

    private void parseShortcut(Element element) {
        Shortcut shortcut = new Shortcut(element);
        mShortcutClass.addShortcut(shortcut);
    }

    private boolean isValid(Element element) {
        if (element.getKind() != ElementKind.CLASS) {
            return false;
        }

        TypeElement typeElement = (TypeElement) element;
        String className = typeElement.getQualifiedName().toString();
        String superClassName = typeElement.getSuperclass().toString();

        if (!(superClassName.endsWith("Activity") || className.endsWith("Activity"))) {
            printError("The annotation need used in Activity class");
            return false;
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ShortcutApplication.class.getCanonicalName());
        types.add(AppShortcut.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void printNote(String message) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void printError(String error) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, error);
    }
}
