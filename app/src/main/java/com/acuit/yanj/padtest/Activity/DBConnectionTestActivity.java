package com.acuit.yanj.padtest.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class DBConnectionTestActivity extends BaseActivity {

    private TextView tvResult;
    private String SQL_SELECT = "select * from menu_order";
    private String SQL_REPLACE = "replace into menu_order " +
            "(device_id,device_code,status,dish_id,dish_code,dish_name,price,create_date) " +
            "values(5,4,0,369,'369','东坡肉',3.50,0) ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvResult = (TextView) findViewById(R.id.tv_test);

        /**
         *
         * */
        testDb();
//        testDisplay();

        System.out.println("aaa onCreate()");
    }

    private void testDisplay() {
        tvResult = (TextView) findViewById(R.id.tv_test);
        System.out.println("aaa onCreate()");
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        double diagonalPixels = Math.sqrt(Math.pow(width, 2)+Math.pow(height, 2)) ;
        double screenSize = diagonalPixels/(160*density) ;


        tvResult.setText("屏幕密度（0.75 / 1.0 / 1.5）:"+density+"    屏幕密度DPI（120 / 160 / 240）:"+densityDpi+"  screenSize:"+screenSize);
/**
 *  结论 ：3dp = 2px
 * */
    }

    private void testDb() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    System.out.println("aaa 主线程获取msg成功");
                    String str = (String) msg.obj;
                    tvResult.setText(str);
                }
                if (msg.what == 0) {
                    System.out.println("aaa resultSet 不成功");
                }

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection dbConnection = DBConnect_Util.getDBConnection();
                Statement statement = null;
                ResultSet resultSet = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    statement = dbConnection.createStatement();
                    resultSet = statement.executeQuery(SQL_SELECT);
                    while (resultSet.next()) {
                        System.out.println("aaa " + stringBuilder.toString());
                        stringBuilder.append(resultSet.getString("dish_name") + " ");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        statement.close();
                        dbConnection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                Message msg = Message.obtain();
                if (0 != stringBuilder.length()) {
                    msg.what = 1;
                    String str = stringBuilder.toString();
                    System.out.println("aaa str:" + str);
                    msg.obj = str;
                    System.out.println("aaa 传值成功");
                } else {
                    msg.what = 0;
                }
                handler.sendMessage(msg);

            }
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("aaa onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("aaa onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("aaa onPause()");
    }
}
