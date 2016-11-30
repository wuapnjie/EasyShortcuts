package com.xiaopo.flying.easyshortcuts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiaopo.flying.annotation.AppShortcut;
import com.xiaopo.flying.annotation.ShortcutApplication;

@AppShortcut(resId = R.mipmap.ic_launcher,
        description = "Second Shortcut",
        rank = 1)
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
