package com.zjxl.yanj.padtest.Model;

import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: HoleDAO <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 13:41 <p>
 * 描述:  餐眼表DB操作类
 * <p>
 * 通过sql语句操作数据库——餐眼表
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class HoleDAO {


    public static final int FLAG_EXIST_UUID = 4;
    public static final int FLAG_EXIST_ROW_NUM = 5;

    public static final int FLAG_ERROR = 2;
    public static final int FLAG_SUCCESS = 3;


    private Connection connection;

    public HoleDAO() {
        this.connection = DBConnect_Util.getDBConnection();
    }


    /**
     * @return 从数据库获取的餐眼集合
     */
    public List<Hole> getAllHoles() {

        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("* ");
        sql.append("from ");
        sql.append("menu_device ");

        List<Hole> holesList = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            holesList = new ArrayList<Hole>();
            while (resultSet.next()) {

                Hole hole = new Hole();

                hole.setId(resultSet.getInt("id"));
                hole.setUuid(resultSet.getString("uuid"));
                hole.setPassword(resultSet.getString("password"));
                hole.setStatu(resultSet.getInt("statu"));
                hole.setLastloginip(resultSet.getString("lastloginip"));
                hole.setLastlogintime(resultSet.getInt("lastlogintime"));
                hole.setSocketFd(resultSet.getInt("socket_fd"));
                hole.setLineId(resultSet.getInt("row"));     //所属餐线id
                hole.setNum(resultSet.getInt("num"));    //餐线内序号
                hole.setDepId(resultSet.getInt("dep_id"));       //餐厅、食堂id
                hole.setLastLiveTime(resultSet.getInt("last_live_time"));
                hole.setOnLine(resultSet.getInt("on_line"));
                hole.setName(resultSet.getString("name"));       //餐眼名称

                holesList.add(hole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("数据库关闭失败");
            }
        }
        return holesList;
    }


    public List<Hole> getHolesByLineName(String linesName) {

        StringBuilder sql = new StringBuilder();
//        sql.append("select * FROM menu_device WHERE row = ( select id from menu_rows where name = '一号线' ) ");
        sql.append("select  *  from ");
        sql.append("menu_device ");
        sql.append("WHERE ");
        sql.append("row = ( select id from menu_rows where name = '");
        sql.append(linesName);
        sql.append("' )  ");

        System.out.println("aaa getHolesByLineName sql:" +sql.toString());

        List<Hole> holesList = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            holesList = new ArrayList<Hole>();
            while (resultSet.next()) {

                Hole hole = new Hole();

                hole.setId(resultSet.getInt("id"));
                hole.setUuid(resultSet.getString("uuid"));
                hole.setPassword(resultSet.getString("password"));
                hole.setStatu(resultSet.getInt("statu"));
                hole.setLastloginip(resultSet.getString("lastloginip"));
                hole.setLastlogintime(resultSet.getInt("lastlogintime"));
                hole.setSocketFd(resultSet.getInt("socket_fd"));
                hole.setLineId(resultSet.getInt("row"));     //所属餐线id
                hole.setNum(resultSet.getInt("num"));    //餐线内序号
                hole.setDepId(resultSet.getInt("dep_id"));       //餐厅、食堂id
                hole.setLastLiveTime(resultSet.getInt("last_live_time"));
                hole.setOnLine(resultSet.getInt("on_line"));
                hole.setName(resultSet.getString("name"));       //餐眼名称

                holesList.add(hole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("数据库关闭失败");
            }
        }
        return holesList;
    }


    /**
     * 添加餐眼
     * 需要校验该餐眼对象是否已存库
     *
     * @param hole
     * @return
     */
    public int addHole(Hole hole) {

//        查询语句
        StringBuilder sql_select_uuid = new StringBuilder();
        sql_select_uuid.append("select ");
        sql_select_uuid.append("* ");
        sql_select_uuid.append("from ");
        sql_select_uuid.append("menu_device ");
        sql_select_uuid.append("where uuid= '");
        sql_select_uuid.append(hole.getUuid());
        sql_select_uuid.append("'");
//        查询语句
        StringBuilder sql_select_row_num = new StringBuilder();
        sql_select_row_num.append("select ");
        sql_select_row_num.append("* ");
        sql_select_row_num.append("from ");
        sql_select_row_num.append("menu_device ");
        sql_select_row_num.append("where row = '");
        sql_select_row_num.append(hole.getLineId());
        sql_select_row_num.append("' and num = '");
        sql_select_row_num.append(hole.getNum());
        sql_select_row_num.append("'");

//        插入语句
        StringBuilder sql_insert = new StringBuilder();
        sql_insert.append("insert into ");
        sql_insert.append("menu_device(");
        sql_insert.append("uuid,row,num,dep_id,name");
        sql_insert.append(") value(");

        sql_insert.append("'" + hole.getUuid() + ",'");
        sql_insert.append(hole.getLineId() + ",");
        sql_insert.append(hole.getNum() + ",'");
        sql_insert.append("'" + hole.getName() + ",'");

        sql_insert.append(") ");

        System.out.println("aaa sql_select_uuid:" + sql_select_uuid);
        System.out.println("aaa sql_select_row_num:" + sql_select_row_num);
        System.out.println("aaa sql_insert:" + sql_insert);
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        int flag = FLAG_ERROR;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_select_uuid.toString());
            if (resultSet.next()) {
                flag = FLAG_EXIST_UUID;
            } else {
                resultSet1 = statement.executeQuery(sql_select_row_num.toString());
                if (resultSet1.next()) {
                    flag = FLAG_EXIST_ROW_NUM;
                } else {

                    int effectRows = statement.executeUpdate(sql_insert.toString());
                    if (effectRows > 0) {
                        flag = FLAG_SUCCESS;
                    } else {
                        flag = FLAG_ERROR;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != resultSet) {
                    resultSet.close();
                }
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("数据库关闭失败");
            }
        }
        return flag;
    }

//
//    private void getTime(String text, long currentTime) {
//        date = new Date(currentTime);
//        if (null == simpleDateFormat) {
//            simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
//        }
//        time = simpleDateFormat.format(date);
//        System.out.println(text + time);
//    }


}
