# EasyShortcuts
一个简单的编译时注解项目，方便产生App Shortcut

对于第一个打开的Activity使用`ShortcutApplication`注解

对于每个希望用Shortcut打开的Activity，使用`AppShortcut`注解

相关博客：[做一个简单的APT小项目——AppShortcut](http://www.jianshu.com/p/46fcb9f8d93a)

### Demo
```java
@ShortcutApplication
@AppShortcut(resId = R.drawable.shortcut,
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
```
### 效果
![](https://github.com/wuapnjie/EasyShortcuts/blob/master/screenshots/shortcut.gif)


