package io.github.seriouszyx.trivialim.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.seriouszyx.trivialim.model.bean.UserInfo;
import io.github.seriouszyx.trivialim.model.dao.UserAccountDao;
import io.github.seriouszyx.trivialim.model.db.DBManager;

// 数据模型层全局类
public class Model {
    private Context mContext;
    private ExecutorService executors = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;

    // 创建对象
    private static Model model = new Model();
    private DBManager dbManager;

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
    public void loginSuccess(UserInfo account) {
        // 校验
        if (account == null) {
            return;
        }

        if (dbManager != null) {
            dbManager.close();
        }

        dbManager = new DBManager(mContext, account.getName());
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    // 获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao() {
        return userAccountDao;
    }
}
