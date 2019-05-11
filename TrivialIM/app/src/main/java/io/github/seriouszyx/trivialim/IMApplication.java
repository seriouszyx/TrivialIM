package io.github.seriouszyx.trivialim;

import android.app.Application;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

public class IMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化EaseUI
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置许耀桐以后才能接受群邀请
        options.setAutoAcceptGroupInvitation(false);


        EaseUI.getInstance().init(this, options);
    }
}
