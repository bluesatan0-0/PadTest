package com.zjxl.yanj.padtest.Model.DAO;

import com.zjxl.yanj.padtest.Bean.Plate;
import com.zjxl.yanj.padtest.Utils.DBConnect_Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: PlateDAO <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/8/7 12:22 <p>
 * 描述:         排菜，数据库表，操作类
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class PlateDAO {


    private Connection connection;

    public PlateDAO() {

        this.connection = DBConnect_Util.getDBConnection();
    }


    /**
     * 获取排菜成功的餐眼集合
     *
     * @return 餐眼集合（排菜成功状态）含菜品基本信息
     */
    public List<Plate> getPlates() {

        StringBuilder sql_select = new StringBuilder();

        sql_select.append(" select * from menu_order where status=1 and device_code in (select menu_device.uuid from menu_device)");

        List<Plate> plates = new ArrayList<Plate>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_select.toString());
            while (resultSet.next()) {

                Plate plate = new Plate();

                plate.setId(resultSet.getString("id"));
                plate.setDevice_id(resultSet.getInt("device_id"));
                plate.setDevice_code(resultSet.getString("device_code"));
                plate.setDish_id(resultSet.getInt("dish_id"));
                plate.setDish_code(resultSet.getString("dish_code"));
                plate.setDish_name(resultSet.getString("dish_name"));
                plate.setPrice(resultSet.getBigDecimal("price"));
                plate.setCreate_date(resultSet.getInt("create_date"));
                plate.setStatus(resultSet.getInt("status"));
                plate.setLeft_amount(resultSet.getBigDecimal("left_amount"));
                plate.setKcal(resultSet.getBigDecimal("kcal"));
                plate.setKcal_nrv(resultSet.getBigDecimal("kcal_nrv"));
                plate.setMenu_url(resultSet.getString("menu_url"));

                plates.add(plate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plates;
    }

    /**
     * 按餐线名称，从数据库查询排菜成功的餐眼
     *
     * @param lineName 餐线名称
     * @return 餐眼集合（排菜成功的）
     */
    public List<Plate> getPlatesByLineName(String lineName) {
        StringBuilder sql_select = new StringBuilder();

        sql_select.append("select * from menu_order where status=1 and device_code in (select menu_device.uuid from menu_device WHERE row = (SELECT id from menu_rows WHERE name = '");
        sql_select.append(lineName);
        sql_select.append("')) ");

        List<Plate> plates = new ArrayList<Plate>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_select.toString());
            while (resultSet.next()) {

                Plate plate = new Plate();

                plate.setId(resultSet.getString("id"));
                plate.setDevice_id(resultSet.getInt("device_id"));
                plate.setDevice_code(resultSet.getString("device_code"));
                plate.setDish_id(resultSet.getInt("dish_id"));
                plate.setDish_code(resultSet.getString("dish_code"));
                plate.setDish_name(resultSet.getString("dish_name"));
                plate.setPrice(resultSet.getBigDecimal("price"));
                plate.setCreate_date(resultSet.getInt("create_date"));
                plate.setStatus(resultSet.getInt("status"));
                plate.setLeft_amount(resultSet.getBigDecimal("left_amount"));
                plate.setKcal(resultSet.getBigDecimal("kcal"));
                plate.setKcal_nrv(resultSet.getBigDecimal("kcal_nrv"));
                plate.setMenu_url(resultSet.getString("menu_url"));

                plates.add(plate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plates;
    }
}
