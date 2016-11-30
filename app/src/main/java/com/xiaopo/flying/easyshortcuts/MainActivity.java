package com.xiaopo.flying.easyshortcuts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiaopo.flying.annotation.AppShortcut;
import com.xiaopo.flying.annotation.ShortcutApplication;
import com.xiaopo.flying.easyshortcuts_api.ShortcutCreator;

@ShortcutApplication
@AppShortcut(resId = R.mipmap.ic_launcher,
        description = "First Shortcut",
        rank = 3)
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShortcutCreator.create(this);
    }
}
