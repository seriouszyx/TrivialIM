package io.github.seriouszyx.trivialim.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.seriouszyx.trivialim.IMApplication;

// 保存和获取
public class SpUtils {

    public static final String IS_NEW_INVITE = "is_new_invite"; // 新的邀请标记
    private static SpUtils instance = new SpUtils();
    private static SharedPreferences mSp;

    private SpUtils() {

    }

    // 单例
    public static SpUtils getInstance() {
        if (mSp == null) {
            mSp = IMApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }
        return instance;
    }

    // 保存
    public void save(String key, Object value) {
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer){
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    // 获取数据的方法
    public String getString(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    // 获取boolean类型数据
    public boolean getBoolean(String key, Boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    // 获取int类型数据
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

}
