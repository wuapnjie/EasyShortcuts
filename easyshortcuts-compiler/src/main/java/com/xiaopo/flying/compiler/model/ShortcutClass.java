package com.xiaopo.flying.compiler.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by snowbean on 16-11-29.
 */
public class ShortcutClass {
    private TypeElement mTypeElement;
    private Elements mElementUtils;
    private List<Shortcut> mShortcuts = new ArrayList<>();

    private static final String SUFFIX = "$$Shortcut";
    private static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    private static final ClassName CREATOR = ClassName.get("com.xiaopo.flying.easyshortcuts_api", "Creator");
    private static final ClassName SHORTCUT_MANAGER = ClassName.get("android.content.pm", "ShortcutManager");
    private static final ClassName SHORTCUT_INFO = ClassName.get("android.content.pm", "ShortcutInfo");
    private static final ClassName INTENT = ClassName.get("android.content", "Intent");
    private static final ClassName ICON = ClassName.get("android.graphics.drawable", "Icon");

    public ShortcutClass(TypeElement element, Elements elements) {
        this.mTypeElement = element;
        this.mElementUtils = elements;
    }

    public void addShortcut(Shortcut shortcut) {
        mShortcuts.add(shortcut);
    }

    public JavaFile generateCode() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(CONTEXT, "context")
                .addStatement("$T shortcutManager = context.getSystemService($T.class)", SHORTCUT_MANAGER, SHORTCUT_MANAGER)
                .addStatement("$T.Builder builder",SHORTCUT_INFO)
                .addStatement("$T intent",INTENT);

        for (Shortcut shortcut : mShortcuts) {
            methodBuilder.
                    addStatement("builder = new $T.Builder(context,$S)", SHORTCUT_INFO, shortcut.getTypeElement().getSimpleName().toString())
                    .addStatement("intent = new $T(context, $T.class)", INTENT, TypeName.get(shortcut.getTypeElement().asType()))
                    .addStatement("intent.setAction($S)", shortcut.getAction())
                    .addStatement("builder.setIntent(intent)")
                    .addStatement("builder.setShortLabel($S)", shortcut.getDescription())
                    .addStatement("builder.setLongLabel($S)", shortcut.getDescription())
                    .addStatement("builder.setRank($L)", shortcut.getRank())
                    .addStatement("builder.setIcon($T.createWithResource(context, $L))", ICON, shortcut.getResId())
                    .addStatement("shortcutManager.addDynamicShortcuts(singletonList(builder.build()))");
        }

        TypeSpec shortcutClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(CREATOR)
                .addMethod(methodBuilder.build())
                .build();

        String packageName = mElementUtils.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile
                .builder(packageName, shortcutClass)
                .addStaticImport(Collections.class, "singletonList")
                .build();
    }
}
