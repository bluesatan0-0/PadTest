package com.acuit.yanj.padtest.Utils;

import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Plate;

/**
 * 类名: GetPlate <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/8/9 15:02 <p>
 * 描述:
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class GetPlate {

    public GetPlate() {
    }

    public static Plate GetPlate_FromHoleAndDish(Hole hole, Dish dish){
        Plate plate = new Plate();

//        plate.setId(resultSet.getString("id"));
        plate.setDevice_id(hole.getId());
        plate.setDevice_code(hole.getUuid());
        plate.setDish_id(Integer.valueOf(dish.getId()));
        plate.setDish_code(dish.getStock_id()+"");
        plate.setDish_name(dish.getName());
        plate.setPrice(dish.getSell_100gram_price());
        plate.setMenu_url(dish.getPic());
//        plate.setCreate_date(); 存储时获取，而非现在
//        plate.setStatus(0);
//        plate.setLeft_amount(resultSet.getBigDecimal("left_amount"));
//        plate.setKcal(resultSet.getBigDecimal("kcal"));
//        plate.setKcal_nrv(resultSet.getBigDecimal("kcal_nrv"));

        return plate;
    }
}
