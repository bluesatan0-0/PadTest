package com.zjxl.yanj.padtest.Model;

import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: LineDAO <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 13:41 <p>
 * 描述:  餐线表DB操作类
 * <p>
 * 通过sql语句操作数据库——餐线表
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class LineDAO {

    public static final int FLAG_EXIST_LINE_NAME = 1;
    public static final int FLAG_ERROR = 2;
    public static final int FLAG_SUCCESS = 3;


    private Connection connection;

    public LineDAO() {

        this.connection = DBConnect_Util.getDBConnection();
    }


    /**
     * @return 从数据库获取的餐线集合
     */
    public List<Line> getAllLines() {

        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("* ");
        sql.append("from ");
        sql.append("menu_rows ");

        List<Line> lines = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            lines = new ArrayList<Line>();
            while (resultSet.next()) {
                Line line = new Line();
                line.setId(resultSet.getInt("id"));
                line.setCode(resultSet.getString("code"));
                line.setName(resultSet.getString("name"));
                line.setStatus(resultSet.getInt("status"));
                lines.add(line);
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
        return lines;
    }


    /**
     * 检验该餐线对象是否已存库
     *
     * @param line
     * @return
     */
    public int addLine(Line line) {

//        查询语句
        StringBuilder sql_select = new StringBuilder();
        sql_select.append("select ");
        sql_select.append("* ");
        sql_select.append("from ");
        sql_select.append("menu_rows ");
        sql_select.append("where name= '");
        sql_select.append(line.getName());
        sql_select.append("'");

//        插入语句
        StringBuilder sql_insert = new StringBuilder();
        sql_insert.append("insert into ");
        sql_insert.append("menu_rows(");
        sql_insert.append("name");
        sql_insert.append(") ");
        sql_insert.append("value(");

        sql_insert.append("'" + line.getName() + "'");

        sql_insert.append(") ");

        System.out.println("aaa sql_select:" + sql_select);
        System.out.println("aaa sql_insert:" + sql_insert);
        Statement statement = null;
        ResultSet resultSet = null;
        int flag = FLAG_ERROR;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_select.toString());
            if (resultSet.next()) {
                flag = FLAG_EXIST_LINE_NAME;
            } else {

                int effectRows = statement.executeUpdate(sql_insert.toString());
                if (effectRows > 0) {
                    flag = FLAG_SUCCESS;
                } else {
                    flag = FLAG_ERROR;
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

    /**
     * 删除餐线
     *
     * @param line
     * @return 返回删除是否成功(LineDao.FLAG)
     */
    public int deleteLine(Line line) {

        //        删除餐眼语句
        StringBuilder sql_deleteHole = new StringBuilder();
        sql_deleteHole.append("delete from menu_device where row = ( ");
        sql_deleteHole.append("select id from menu_rows where name = '");
        sql_deleteHole.append(line.getName());
        sql_deleteHole.append("')");

        //        删除餐线语句
        StringBuilder sql_deleteLine = new StringBuilder();
        sql_deleteLine.append("delete ");
        sql_deleteLine.append("from ");
        sql_deleteLine.append("menu_rows ");
        sql_deleteLine.append("where name= '");
        sql_deleteLine.append(line.getName());
        sql_deleteLine.append("'");

        System.out.println("aaa sql_deleteHole:" + sql_deleteHole);
        System.out.println("aaa sql_deleteLine:" + sql_deleteLine);
        Statement statement = null;
        ResultSet resultSet = null;
        int flag = FLAG_ERROR;

        try {
            statement = connection.createStatement();
            int effectRows = statement.executeUpdate(sql_deleteHole.toString());
            if (effectRows >= 0) {
                effectRows = statement.executeUpdate(sql_deleteLine.toString());
                if (effectRows >= 0) {
                    flag = FLAG_SUCCESS;
                } else {
                    flag = FLAG_ERROR;
                }
            } else {
                flag = FLAG_ERROR;
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
