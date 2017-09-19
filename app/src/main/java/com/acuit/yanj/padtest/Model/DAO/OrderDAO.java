package com.acuit.yanj.padtest.Model.DAO;

import com.acuit.yanj.padtest.Bean.Trade;
import com.acuit.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: OrderDAO <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017-09-19 16:30 <p>
 * 描述:  交易记录表 DB操作类
 * <p>
 * 通过sql语句操作数据库——交易记录表
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class OrderDAO {

    public static final int FLAG_ERROR = 2;
    public static final int FLAG_SUCCESS = 3;


    private Connection connection;

    public OrderDAO() {

        this.connection = DBConnect_Util.getDBConnection();
    }


    /**
     * @return 从数据库获取的交易记录
     */
    public List<Trade> getOrders() {

        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("* ");
        sql.append("from ");
        sql.append("menu_trade ");

        List<Trade> trades = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            trades = new ArrayList<Trade>();
            while (resultSet.next()) {
                Trade trade = new Trade();
                trade.setId(resultSet.getString("id"));
                trade.setCode(resultSet.getString("code"));
                trade.setDeviceId(resultSet.getInt("device_id"));
                trade.setUserId(resultSet.getInt("user_id"));
                trade.setRfid(resultSet.getString("rfid"));
                trade.setDishId(resultSet.getInt("dish_id"));
                trade.setPrice(resultSet.getBigDecimal("price"));
                trade.setAmount(resultSet.getInt("amount"));
                trade.setMoney(resultSet.getBigDecimal("money"));
                trade.setCreateDate(resultSet.getInt("create_date"));
                trade.setIsUpload(resultSet.getInt("is_upload"));
                trade.setUpdoadDate(resultSet.getInt("updoad_date"));
                trade.setLeftAmount(resultSet.getInt("left_amount"));
                trade.setDeviceCode(resultSet.getString("device_code"));

                trades.add(trade);
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
        return trades;
    }



}
