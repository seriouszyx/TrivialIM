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

        //初始化easeui
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false); //设置需要同意后才能接受邀请
        options.setAutoAcceptGroupInvitation(false); //设置需要同意后才能接受群邀请
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        EaseUI.getInstance().init(this,options);

        //初始化数据模型层类
        Model.getInstance().init(this);

        //初始化全局上下文
        mContext = this;
    }

    // 获取全局上下文对象
    public static Context getGlobalApplication() {
        return mContext;
    }
}
