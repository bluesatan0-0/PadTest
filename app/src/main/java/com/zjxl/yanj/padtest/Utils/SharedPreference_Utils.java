package com.zjxl.yanj.padtest.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import java.util.Set;

/**
 * 类名: SharedPreference_Utils <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/15 16:01 <p>
 * 描述: sharedPreference的工具类，键值对的键名
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SharedPreference_Utils {

    //    键值对的键名

    public static final String KEY_ADMIN_NAME = "KEY_ADMIN_NAME";
    public static final String KEY_ADMIN_PWD = "KEY_ADMIN_PWD";

    public static final String KEY_REMOTE_SERVER_IP = "KEY_REMOTE_SERVER_IP";
    public static final String KEY_REMOTE_SERVER_DEPATEMENT_CODE = "KEY_REMOTE_SERVER_DEPATEMENT_CODE";

    public static final String KEY_DB_IP = "KEY_DB_IP";
    public static final String KEY_DB_PORT = "KEY_DB_PORT";
    public static final String KEY_DB_NAME = "KEY_DB_NAME";
    public static final String KEY_DB_CHARATER_SET = "KEY_DB_CHARATER_SET";
    public static final String KEY_DB_USER = "KEY_DB_USER";
    public static final String KEY_DB_PWD = "KEY_DB_PWD";
//    以上为sharedPreference键值对中key名与key值

    public static final String[] KEYS = new String[]
            {"KEY_REMOTE_SERVER_IP",
                    "KEY_REMOTE_SERVER_DEPATEMENT_CODE",
                    "KEY_ADMIN_NAME",
                    "KEY_ADMIN_PWD",
                    "KEY_DB_IP",
                    "KEY_DB_PORT",
                    "KEY_DB_NAME",
                    "KEY_DB_CHARATER_SET",
                    "KEY_DB_USER",
                    "KEY_DB_PWD"
            };


    private static SharedPreference_Utils instance;

    private static SharedPreferences sharedPreferences;

    private static ArrayMap<String, String> configs;

    private static Context context;


    //    构造器
    private SharedPreference_Utils(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("configs", context.MODE_PRIVATE);
        this.configs = getValuesALL();

    }

    //    单例
    public synchronized static SharedPreference_Utils getInstance(Context context) {
        if (null == instance) instance = new SharedPreference_Utils(context);
        return instance;
    }

    //获取某项参数
    public static String getValue(String key) {
        return sharedPreferences.getString(key, "ERROR");
    }

    //获取全部参数(若为空，则使用URL_Util中的默认值)
    public static ArrayMap<String, String> getValuesALL() {

        ArrayMap<String, String> configs = new ArrayMap<>();

        configs.put(KEY_REMOTE_SERVER_IP, sharedPreferences.getString(KEY_REMOTE_SERVER_IP, URL_Util.REMOTE_SERVER_IP_TEST));
        configs.put(KEY_REMOTE_SERVER_DEPATEMENT_CODE, sharedPreferences.getString(KEY_REMOTE_SERVER_DEPATEMENT_CODE, URL_Util.REMOTE_SERVER_DEPARTMENT_CODE_TEST));

        configs.put(KEY_DB_IP, sharedPreferences.getString(KEY_DB_IP, URL_Util.DB_IP_TEST));
        configs.put(KEY_DB_PWD, sharedPreferences.getString(KEY_DB_PWD, URL_Util.DB_PWD_TEST));
        configs.put(KEY_DB_PORT, sharedPreferences.getString(KEY_DB_PORT, URL_Util.DB_PORT_TEST));
        configs.put(KEY_DB_NAME, sharedPreferences.getString(KEY_DB_NAME, URL_Util.DB_NAME_TEST));
        configs.put(KEY_DB_USER, sharedPreferences.getString(KEY_DB_USER, URL_Util.DB_USER_TEST));
        configs.put(KEY_DB_CHARATER_SET, sharedPreferences.getString(KEY_DB_CHARATER_SET, URL_Util.DB_CHARACTER_ENCODE));

        // TODO: 2017/7/28 默认管理员账户
        configs.put(KEY_ADMIN_NAME, sharedPreferences.getString(KEY_ADMIN_NAME, ""));
        configs.put(KEY_ADMIN_PWD, sharedPreferences.getString(KEY_ADMIN_PWD, ""));


        return configs;
    }

    //设置某项参数，返回操作结果
    public static boolean setValue(String key, String value) {

        boolean commit = sharedPreferences.edit().putString(key, value).commit();
//        提交成功则更新app中config
        if (commit) {
            setConfigs(getValuesALL());
        }
        return commit;
    }

    //批量设置参数，返回操作结果
    public static boolean setValues(ArrayMap<String, String> data) {
        SharedPreferences.Editor edit = sharedPreferences.edit();

        Set<String> keySet = data.keySet();
        for (String s : keySet) {
            edit.putString(s, data.get(s));
            System.out.println("aaa configs:" + s + "  " + data.get(s));
        }
//        提交成功则更新app中config
        boolean result = edit.commit();
        if (result) {
            setConfigs(getValuesALL());
        }

        return result;
    }

    public static ArrayMap<String, String> getConfigs() {
        if (null == configs) {
            return getInstance(context).getConfigs();
        }
        return configs;
    }

    public static void setConfigs(ArrayMap<String, String> configs) {
        SharedPreference_Utils.configs = configs;
    }
}
