package io.github.seriouszyx.trivialim.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.seriouszyx.trivialim.model.dao.UserAccountDao;

// 数据模型层全局类
public class Model {
    private Context mContext;
    private ExecutorService executors = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;

    // 创建对象
    private static Model model = new Model();

    // 私有化构造
    private Model() {

    }

    // 获取单例对象
    public static Model getInstance() {

        return model;
    }

    // 初始化的方法
    public void init(Context context) {
        mContext = context;

        // 创建用户账号数据库的操作类对象
        userAccountDao = new UserAccountDao(mContext);
    }

    // 获取全局线程池对象
    public ExecutorService getGlobalThreadPool() {
        return executors;
    }

    // 用户登陆成功后的处理方法
    public void loginSuccess() {
    }

    // 获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao() {
        return userAccountDao;
    }
}
