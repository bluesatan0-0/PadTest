package com.acuit.yanj.padtest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.SharedPreference_Utils;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

/**
 * 类名: FirstActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/21 15:28 <p>
 * 描述: 动画页面
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dispatchActivity();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    private void dispatchActivity() {

        ArrayMap<String, String> configs = (SharedPreference_Utils.getInstance(this)).getConfigs();

        Intent intent = null;

        // TODO: 2017/7/24 编写测试模块时使用，完成测试模块后使用?👆
        if (configs.get(SharedPreference_Utils.KEY_DB_IP).isEmpty()||configs.get(SharedPreference_Utils.KEY_REMOTE_SERVER_IP).isEmpty()) {
//        if(true){
            System.out.println("aaa ip为测试ip，进入测试");
//            未设置前置机、服务器
//            初次打开，进入设置模块登录界面
            intent = new Intent(this, LoginActivity.class);

        } else {
//            非初次，获取参数信息，直接进入主界面 需要获取：
            // TODO: 2017/7/24 进入主页模块
            System.out.println("aaa 进入主页模块");
            intent = new Intent(this, MainActivity.class);

        }


        final Intent finalIntent = intent;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                startActivity(finalIntent);
            }
        };

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {
                handler.sendMessageDelayed(Message.obtain(), 1000);
            }
        });

    }
}
