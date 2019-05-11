package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.model.Model;

// 登录页面
public class LoginAcitivity extends Activity {

    private EditText et_login_name;
    private EditText et_login_pwd;
    private Button bt_login_regist;
    private Button bt_login_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        // 初始化控件
        initView();

        // 初始化监听
        initListener();
    }

    private void initListener() {
        // 注册按钮的点击事件处理
        bt_login_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

        // 登录按钮的点击事件处理
        bt_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    // 登录按钮的业务逻辑处理
    private void login() {
        
    }

    // 注册业务的逻辑处理
    private void regist() {
        // 获取输入的用户名和密码
        final String registName = et_login_name.getText().toString();
        final String registPwd = et_login_pwd.getText().toString();
        // 校验输入的用户名和密码
        if (TextUtils.isEmpty(registName) || TextUtils.isEmpty(registPwd)) {
            Toast.makeText(this, "输入的用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 去服务器注册账号
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(registName + " " + registPwd);
                    EMClient.getInstance().createAccount(registName, registPwd);
                    // 注册成功，更新页面显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAcitivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAcitivity.this, "注册失败"+e.toString()+e.getErrorCode(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
         et_login_name = findViewById(R.id.et_login_name);
         et_login_pwd = findViewById(R.id.et_login_pwd);
         bt_login_regist = findViewById(R.id.bt_login_regist);
         bt_login_login = findViewById(R.id.bt_login_login);
    }
}
