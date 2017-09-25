package com.acuit.yanj.padtest.Model.DAO;

import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Utils.DBConnect_Util;

import java.math.BigDecimal;
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
                dish.setSell_100gram_price(resultSet.getDouble("sell_100gram_price"));
                dish.setAmount(resultSet.getBigDecimal("amount"));
                dish.setPic(resultSet.getString("pic"));
                dish.setCate(resultSet.getInt("cate"));
                dish.setCat_name(resultSet.getString("cat_name"));
                dish.setDate(resultSet.getString("date"));
                dish.setPart(resultSet.getInt("part"));
                dish.setName(resultSet.getString("name"));
                if (null!=resultSet.getString("kcal")) {
                    dish.setKcal(BigDecimal.valueOf(Float.parseFloat(resultSet.getString("kcal"))));
                }
                if (null!=resultSet.getString("kcal_nrv")) {
                    dish.setKcal_nrv(BigDecimal.valueOf(Float.parseFloat(resultSet.getString("kcal_nrv"))));
                }

                dishes.add(dish);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dishes;
    }


    /**
     * 保存 下载的 今日菜单
     *
     * @param dishesList 今日菜单
     * @return 操作结果
     */
    public boolean save(ArrayList<Dish> dishesList) {

        String date = dishesList.get(0).getDate();
        Integer part = dishesList.get(0).getPart();
        boolean b = deleteSameDishes(date, part);
        if (b) {
            return saveAfterDelete(dishesList);
        } else {
            return saveAfterDelete(dishesList);
        }

    }

    /**
     * 保存 下载的 今日菜单
     *
     * @param dishesList 今日菜单
     * @return 操作结果
     */
    public boolean saveAfterDelete(ArrayList<Dish> dishesList) {


        Statement statement = null;
        StringBuilder sql = new StringBuilder();


        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            for (Dish dish : dishesList) {
                sql.replace(0, sql.length(), "");

//                System.out.println("aaa sql存储前：" + dish);
//                INSERT INTO menu_plan_stock
//                        (stock_id,price,sell_100gram_price,amount,pic,cate,cat_name,date,part,name)
//                VALUES(2000,20.00,5.00,2.00,'http;//.jpg',1,'卤味','2017-07-15',1,'酱猪蹄')

                sql.append(" REPLACE INTO menu_plan_stock ");
//                sql.append(" INSERT INTO menu_plan_stock ");
                sql.append(" (stock_id,price,sell_100gram_price,amount,cate,part,pic,cat_name,date,name,kcal,kcal_nrv) VALUES(");

                sql.append(dish.getStock_id());
                sql.append(",");
                sql.append(dish.getPrice());
                sql.append(",");
                sql.append(dish.getSell_100gram_price());
                sql.append(",");
                sql.append(dish.getAmount());
                sql.append(",");

                sql.append(dish.getCate());
                sql.append(",");
                sql.append(dish.getPart());
                sql.append(",'");
                sql.append(dish.getPic());
                sql.append("','");
                sql.append(dish.getCat_name());
                sql.append("','");
                sql.append(dish.getDate());
                sql.append("','");
                sql.append(dish.getName());
                sql.append("','");
                sql.append(dish.getKcal());
                sql.append("','");
                sql.append(dish.getKcal_nrv());
                sql.append("')");

//                System.out.println("aaa 对应sql：" + sql.toString());
                int i = statement.executeUpdate(sql.toString());
                if (0 >= i) {
                    return false;
                }
            }
            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                return true;
            }
        }

    }

    /**
     * 插入今日菜品前先执行删除，避免插入失败
     *
     * @param date
     * @param part
     */
    private boolean deleteSameDishes(String date, Integer part) {

        Statement statement = null;
        StringBuilder sql = new StringBuilder();

        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();

//            DELETE FROM table_name [WHERE Clause]
            sql.append(" DELETE FROM menu_plan_stock ");
            sql.append(" where date='");
            sql.append(date);
            sql.append("' and part = ");
            sql.append(part + "");

            System.out.println("aaa delete sql:" + sql.toString());
            boolean execute = statement.execute(sql.toString());
            connection.commit();

            return execute;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }


}
