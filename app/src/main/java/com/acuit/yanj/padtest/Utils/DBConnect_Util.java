package com.acuit.yanj.padtest.Utils;

import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.acuit.yanj.padtest.Base.BaseApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 类名: DBConnect_Util <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/15 14:38 <p>
 * 描述:  连接Mysql数据库的工具类
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class DBConnect_Util {


    private static String TAG = "aaa";
//    private static Connection connection = null;

    private String SQL_SELECT = "select * from menu_plan_stock";
    private String SQL_REPLACE = "replace into menu_order " +
            "(device_id,device_code,status,dish_id,dish_code,dish_name,price,create_date) " +
            "values(5,4,0,369,'369','东坡肉',3.50,0) ";


    //    获取Mysql数据库连接，须在子线程中使用。
//    public synchronized static Connection getDBConnection() {
    public  static Connection getDBConnection() {

        ArrayMap<String, String> configs = SharedPreference_Utils.getConfigs();


        String db_url = URL_Util.getDB_URL(configs.get(SharedPreference_Utils.KEY_DB_IP), configs.get(SharedPreference_Utils.KEY_DB_PORT), configs.get(SharedPreference_Utils.KEY_DB_NAME));
        String db_user = configs.get(SharedPreference_Utils.KEY_DB_USER);
        String db_pwd = configs.get(SharedPreference_Utils.KEY_DB_PWD);
        Connection connection = null;
        try {

            Class.forName(URL_Util.DB_DRIVER_CLASS_PATH);
//            System.out.println("aaa 成功加载MySQL驱动程序");

//            String path = "jdbc:mysql://120.26.88.91:3306/my_server?useUnicode=true&characterEncoding=UTF8";
//            System.out.println("aaa path:" + db_url + " user:" + db_user + " pwd:" + db_pwd);
            connection = DriverManager.getConnection(db_url, db_user, db_pwd);
//            connection = DriverManager.getConnection(path);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "aaa: 获取jdbc包失败");
            e.printStackTrace();
        } catch (SQLException e) {
            Toast.makeText(BaseApplication.getInstance(), "连接数据库失败，请检查网络！", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "aaa: 创建MySqlconnection失败");
            e.printStackTrace();
        }
        return connection;
    }


//    //    关闭数据库连接
//    public static boolean closeDBConnection() {
//
//        try {
//            if (null != connection) {
//                connection.close();
//                return true;
//            } else {
//                System.out.println(TAG + "数据库连接为空");
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Log.e(TAG, "closeDBConnection: falses");
//            return false;
//        }
//
//    }

}