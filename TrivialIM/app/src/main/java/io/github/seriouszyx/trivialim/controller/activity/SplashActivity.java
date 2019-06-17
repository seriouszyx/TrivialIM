package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.hyphenate.chat.EMClient;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.model.Model;
import io.github.seriouszyx.trivialim.model.bean.UserInfo;

// 欢迎页面
public class SplashActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 如果当前activity已经退出，则不处理handler中的消息
            if (isFinishing()) {
                return;
            }

            // 判断进入主页面还是登录页面
            toMainOrLogin();
        }
    };

    /**
     *   判断进入主页面还是登录页面
     */
    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 判断此账号是否已经登陆过
                if (EMClient.getInstance().isLoggedInBefore()) {
                    // 登陆过
                    // 获取到当前登录用户的信息
                    UserInfo account = Model.getInstance().getUserAccountDao().
                            getAccountByHxId(EMClient.getInstance().getCurrentUser());
                    if (account == null) {
                        // 跳转到登录页面
                        Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
                        startActivity(intent);
                    } else {
                        // 登录成功后的方法
                        Model.getInstance().loginSuccess(account);
                        // 跳转到主页面
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // 没登录过
                    // 跳转到登录页面
                    Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
                    startActivity(intent);
                }

                // 结束当前页面
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // 发送2秒钟的延时消息
        handler.sendMessageDelayed(Message.obtain(), 2000);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁消息
        handler.removeCallbacksAndMessages(null);
    }
}
