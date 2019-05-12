package io.github.seriouszyx.trivialim.model.db;

import android.content.Context;

import io.github.seriouszyx.trivialim.model.dao.ContactTableDao;
import io.github.seriouszyx.trivialim.model.dao.InviteTableDao;

// 联系人和邀请信息表的操作类的管理类
public class DBManager {

    private final DBHelper dbHelper;
    private final ContactTableDao contactTableDao;
    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name) {
        // 创建数据库
        dbHelper = new DBHelper(context, name);

        // 创建该数据库中两张表的操作类
        contactTableDao = new ContactTableDao(dbHelper);
        inviteTableDao = new InviteTableDao(dbHelper);
    }

    public ContactTableDao getContactTableDao() {
        return contactTableDao;
    }

    public InviteTableDao getInviteTableDao() {
        return inviteTableDao;
    }

    // 关闭数据库的方法
    public void close() {
        dbHelper.close();
    }
}
