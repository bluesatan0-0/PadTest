package com.zjxl.yanj.padtest.Base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zjxl.yanj.padtest.Utils.SharedPreference_Utils;
import com.zjxl.yanj.padtest.Utils.ThreadPool_Util;

import org.litepal.LitePal;

/**
 * 类名: BaseApplication <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 11:45 <p>
 * 描述:
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static RefWatcher mRefWatcher;


    //    获取BaseApplication实例
    public static BaseApplication getInstance() {
        return instance;
    }

    //    获取LeakCanary实例
    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    //    获取应用是否处于调试状态
    public static boolean isDebug(Context context) {
        boolean isDebug = null != context.getApplicationInfo() && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return isDebug;
    }

    // TODO: 2017/7/13 全局变量

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

//        根据应用是否调试，切换LeakCanary内存泄漏工具开关
        mRefWatcher = isDebug(this) ? LeakCanary.install(this) : RefWatcher.DISABLED;

//        初始化LitePal数据库第三方
        LitePal.initialize(this);

//        初始化SharedPreference工具
        SharedPreference_Utils.getInstance(this);



//        初始化线程池——数据访问型（并发线程数量多，用时短）
        ThreadPool_Util.getInstance(this);

//        初始化线程池——数据处理型（并发线程数量少，用时长）

////        定长线程池（服务）
//        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
//            @Override
//            public Thread newThread(@NonNull Runnable r) {
//                return null;
//            }
//        });



    }

}
