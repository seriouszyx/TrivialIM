package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.controller.fragment.ChatFragment;
import io.github.seriouszyx.trivialim.controller.fragment.ContactListFragment;
import io.github.seriouszyx.trivialim.controller.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();
    }

    private void initListener() {
        // RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.rb_main_chat:
                        fragment = chatFragment;
                        break;
                    case R.id.rb_main_contact:
                        fragment = contactListFragment;
                        break;
                    case R.id.rb_main_setting:
                        fragment = settingFragment;
                        break;
                    default:
                        break;
                }

                // 实现fragment切换
                switchFragment(fragment);
            }
        });

        // 默认选择会话列表页面
        rg_main.check(R.id.rb_main_chat);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }

    private void initData() {
        // 创建三个Fragment对象
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();

    }

    private void initView() {
        rg_main = findViewById(R.id.rg_main);
    }
}
