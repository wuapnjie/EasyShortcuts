package com.xiaopo.flying.compiler.model;

import com.xiaopo.flying.annotation.AppShortcut;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by snowbean on 16-11-30.
 */
public class Shortcut {
    private int mResId;
    private int mRank;
    private String mDescription;
    private String mAction;
    private TypeElement mTypeElement;

    public Shortcut(Element element) {
        mTypeElement = (TypeElement) element;
        AppShortcut appShortcut = mTypeElement.getAnnotation(AppShortcut.class);
        mResId = appShortcut.resId();
        mRank = appShortcut.rank();
        mDescription = appShortcut.description();
        mAction = appShortcut.action();
    }

    public int getRank() {
        return mRank;
    }

    public int getResId() {
        return mResId;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAction() {
        return mAction;
    }
}
