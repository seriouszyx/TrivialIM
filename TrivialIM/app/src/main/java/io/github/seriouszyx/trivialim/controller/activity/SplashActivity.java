package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.os.Bundle;

import io.github.seriouszyx.trivialim.R;

// 欢迎页面
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
