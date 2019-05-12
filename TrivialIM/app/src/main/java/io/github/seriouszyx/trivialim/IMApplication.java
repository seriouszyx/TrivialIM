package io.github.seriouszyx.trivialim;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import io.github.seriouszyx.trivialim.model.Model;

public class IMApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化EaseUI
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置同意以后才能接受群邀请
        options.setAutoAcceptGroupInvitation(false);

        EaseUI.getInstance().init(this, options);

        // 初始化数据模型层
        Model.getInstance().init(this);

        // 初始化全局上下文对象
        mContext = this;
    }

    // 获取全局上下文对象
    public static Context getGlobalApplication() {
        return mContext;
    }
}
