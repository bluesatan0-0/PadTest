package com.acuit.yanj.padtest.Model.DAO;

import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: DishDAO <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/8/8 15:10 <p>
 * 描述: 菜单表 menu_plan_stock 操作类
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class DishDAO {


    private Connection connection;

    public DishDAO() {

        this.connection = DBConnect_Util.getDBConnection();
    }


    /**
     * 获取 最近一次下载的 菜单（菜品集合）
     *
     * @return 餐眼集合（排菜成功状态）含菜品基本信息
     */
    public List<Dish> getLastDishes() {

        StringBuilder sql_select = new StringBuilder();

        sql_select.append(" select * from menu_plan_stock ");
        sql_select.append("where date = (select date from menu_plan_stock ORDER BY id desc limit 1)");
        sql_select.append(" and part = (select part from menu_plan_stock ORDER BY id desc limit 1)");

        return getDishes(sql_select.toString());

    }


    /**
     * 根据sql语句
     * 获取排菜成功的餐眼集合
     *
     * @param sql 数据库查询语句 menu_order表
     * @return 餐眼集合（排菜成功状态）含菜品基本信息
     */
    public List<Dish> getDishes(String sql) {

        ArrayList<Dish> dishes = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {

                Dish dish = new Dish();

                String name;

                dish.setId(resultSet.getString("id"));
                dish.setStock_id(resultSet.getInt("stock_id"));
                dish.setPrice(resultSet.getBigDecimal("price"));
                dish.setSell_100gram_price(resultSet.getBigDecimal("sell_100gram_price"));
                dish.setAmount(resultSet.getBigDecimal("amount"));
                dish.setPic(resultSet.getString("pic"));
                dish.setCate(resultSet.getInt("cate"));
                dish.setCat_name(resultSet.getString("cat_name"));
                dish.setDate(resultSet.getString("date"));
                dish.setPart(resultSet.getInt("part"));
                dish.setName(resultSet.getString("name"));

                dishes.add(dish);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dishes;
    }


//    存储新下载的菜单




}
