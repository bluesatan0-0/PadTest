package com.acuit.yanj.padtest.Base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.acuit.yanj.padtest.Utils.MyImageLoader;
import com.acuit.yanj.padtest.Utils.SharedPreference_Utils;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

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
    private RequestQueue mRequestQueue;
    private MyImageLoader mImageLoader;


    /**
     * 获取BaseApplication实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取LeakCanary实例
     *
     * @return
     */
    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    /**
     * 获取volley的请求队列（已完成初始化）
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        return getInstance().mRequestQueue;
    }

    /**
     * 获取图片加载工具（基于volley）
     *
     * @return
     */
    public static MyImageLoader getImageLoader() {
        return getInstance().mImageLoader;
    }

    /**
     * 获取应用是否处于调试状态
     *
     * @param context
     * @return
     */
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


//        volley初始化
        mRequestQueue = Volley.newRequestQueue(this);
//        图片加载器的初始化（基于volley）
        mImageLoader = new MyImageLoader(this);


    }


}
