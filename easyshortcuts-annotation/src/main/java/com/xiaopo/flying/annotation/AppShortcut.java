package com.xiaopo.flying.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by snowbean on 16-11-29.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AppShortcut {
    int resId();

    int rank() default 1;

    String description();

    String action() default Define.SHORTCUT_ACTION;
}
