package com.xiaopo.flying.easyshortcuts_api;

import android.content.Context;

/**
 * Created by snowbean on 16-11-29.
 */
public class ShortcutCreator {
    public static void create(Context context) {
        try {
            Class<?> targetClass = context.getClass();
            Class<?> creatorClass = Class.forName(targetClass.getName() + "$$Shortcut");
            Creator creator = (Creator) creatorClass.newInstance();
            creator.create(context);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
