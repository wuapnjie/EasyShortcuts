package com.xiaopo.flying.easyshortcuts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiaopo.flying.annotation.AppShortcut;

@AppShortcut(resId = R.mipmap.ic_launcher,
        description = "Third Shortcut",
        rank = 2)
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
}
