package com.zjxl.yanj.padtest.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名: URL_Util <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/15 14:02 <p>
 * 描述:  存放常量
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class URL_Util {

    /**
     * DB👇
     */
    public final static String DB_DRIVER_CLASS_PATH = "com.mysql.jdbc.Driver";
    public final static String DB_DRIVER_PATH_SCHEME = "jdbc:mysql:" + File.separator + File.separator;
    public final static String DB_CHARACTER_ENCODE = "useUnicode=true&characterEncoding=UTF8";

    //    测试
    public final static String DB_IP_TEST = "120.26.88.91";
    public final static String DB_PORT_TEST = "3306";
    public final static String DB_NAME_TEST = "my_server";
    public final static String DB_USER_TEST = "yanjiang";
    public final static String DB_PWD_TEST = "yanjiang";

    public final static String DB_URL_TEST = DB_DRIVER_PATH_SCHEME
            + DB_IP_TEST + ":" + DB_PORT_TEST
            + File.separator + DB_NAME_TEST
            + "?" + DB_CHARACTER_ENCODE;

    /**
     * 配合db_user、db_pwd使用
     *
     * @param db_ip   数据库ip
     * @param db_name 数据库名
     * @param db_port 数据库端口
     * @return 返回数据库URL，用于建立数据库连接
     */
    public static String getDB_URL(String db_ip, String db_port, String db_name) {

        return DB_DRIVER_PATH_SCHEME + db_ip + ":" + db_port + File.separator + db_name + "?" + DB_CHARACTER_ENCODE;
    }

    //    返回测试URL（数据库）
    public static String getDB_URL_TEST() {

        return DB_URL_TEST;
    }

    /**
     * DB👆
     * */


    /**
     * SERVER👇
     */
//    菜品清单接口：
//    shop=15餐厅号：之江食堂   shoptimes=2 餐别：中餐
//    http://192.168.2.241/apis.php?c=Z_braindisc&a=platesettings&shop=15&format=json&d=2017-07-15&shoptimes=2

//    远端服务器接口 REMOTE_SERVER_API

    public final static String REMOTE_SERVER_SCHEME = "http:" + File.separator + File.separator;
    public final static String REMOTE_SERVER_SUFFIX = File.separator + "apis.php?c=Z_braindisc&a=platesetting";

    //    测试地址直接使用（已拼装好）
    public final static String REMOTE_SERVER_IP_TEST = "192.168.2.241";
    public final static String REMOTE_SERVER_DEPARTMENT_CODE_TEST = "15";
    public final static String BASE_PATH_TEST = REMOTE_SERVER_SCHEME + REMOTE_SERVER_IP_TEST + REMOTE_SERVER_SUFFIX;
    public final static String DISH_LIST_JSON_URL_TEST = BASE_PATH_TEST + "&shop="+ REMOTE_SERVER_DEPARTMENT_CODE_TEST +"&format=json&d=2017-07-15&shoptimes=2";

    /**
     * 获取远端服务器数据（当前时间菜单）
     *
     * @param remote_server_ip 服务器ip
     * @param shop_code        餐厅编号
     * @return String 返回拼装完的接口URL
     */

    public static String getDishListJson_URL(String remote_server_ip, String shop_code) {

        String basePath = getBasePath_RemoteServer(remote_server_ip);
        String time = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis()));
//        String time =  Long.valueOf(System.currentTimeMillis());
        return basePath + "&shop=" + shop_code + "&format=json&d=" + time;
    }

    public static String getBasePath_RemoteServer(String remote_server_ip) {
        return REMOTE_SERVER_SCHEME + remote_server_ip + REMOTE_SERVER_SUFFIX;
    }

    public static String getDishListJson_URL_Test() {
        return DISH_LIST_JSON_URL_TEST;
    }

    /**
     * SERVER
     * */

}
