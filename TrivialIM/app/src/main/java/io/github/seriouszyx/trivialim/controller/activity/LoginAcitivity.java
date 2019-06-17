package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jaeger.library.StatusBarUtil;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.model.Model;
import io.github.seriouszyx.trivialim.model.bean.UserInfo;

// 登录页面
public class LoginAcitivity extends BaseActivity {

    private EditText et_login_name;
    private EditText et_login_pwd;
    private Button bt_login_regist;
    private Button bt_login_login;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        // 初始化控件
        initView();

        // 初始化监听
        initListener();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
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
        // 获取输入的用户名和密码
        final String loginName = et_login_name.getText().toString();
        final String loginPwd = et_login_pwd.getText().toString();

        // 校验输入的用户名和密码
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(this, "输入的用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器登录
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // 对模型层数据的处理
                        Model.getInstance().loginSuccess(new UserInfo(loginName));
                        // 保存用户账号信息到本地数据库
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 提示登录成功
                                Toast.makeText(LoginAcitivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                // 跳转到主页面
                                Intent intent = new Intent(LoginAcitivity.this, MainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        // 提示登陆失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginAcitivity.this, "登陆失败" + s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
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
