package com.zjxl.yanj.padtest.Bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类名: Trade <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/27 14:49 <p>
 * 描述:  交易记录
 * CREATE TABLE `menu_trade` (
 * `id` int(6) unsigned NOT NULL AUTO_INCREMENT,
 * `code` char(48) DEFAULT '',
 * `device_id` int(11) DEFAULT '0',
 * `user_id` int(11) DEFAULT '0',
 * `rfid` char(32) DEFAULT '',
 * `dish_id` int(11) DEFAULT '0',
 * `price` decimal(12,2) DEFAULT '0.00' COMMENT '每100克的价格，元为单位',
 * `amount` int(11) DEFAULT '0' COMMENT '重量',
 * `money` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
 * `create_date` int(11) DEFAULT '0',
 * `trade_date` int(11) DEFAULT '0' COMMENT '交易日期',
 * `is_upload` tinyint(4) DEFAULT '0' COMMENT '是否已上传到线上服务器',
 * `updoad_date` int(11) DEFAULT '0',
 * `left_amount` int(11) DEFAULT '0' COMMENT '菜盘剩余量',
 * `device_code` varchar(24) DEFAULT '' COMMENT '设备编码',
 * PRIMARY KEY (`id`),
 * UNIQUE KEY `uk_dish_rfid_time` (`rfid`,`dish_id`,`trade_date`) USING BTREE,
 * KEY `uk_code` (`code`) USING BTREE
 * ) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 * <p>
 * <p>
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class Trade implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 578575777499414319L;

    /**  */
    private String id;
    /**  */
    private String code;
    /**  */
    private Integer deviceId;
    /**  */
    private Integer userId;
    /**  */
    private String rfid;
    /**  */
    private Integer dishId;
    /**
     * 每100克的价格，元为单位
     */
    private BigDecimal price;
    /**
     * 重量
     */
    private Integer amount;
    /**
     * 金额
     */
    private BigDecimal money;
    /**  */
    private Integer createDate;
    /**
     * 交易日期
     */
    private Integer tradeDate;
    /**
     * 是否已上传到线上服务器
     */
    private Integer isUpload;
    /**  */
    private Integer updoadDate;
    /**
     * 菜盘剩余量
     */
    private Integer leftAmount;
    /**
     * 设备编码
     */
    private String deviceCode;


    public Trade() {
    }

    public Trade(String id, String code, Integer deviceId, Integer userId, String rfid, Integer dishId, BigDecimal price, Integer amount, BigDecimal money, Integer createDate, Integer tradeDate, Integer isUpload, Integer updoadDate, Integer leftAmount, String deviceCode) {
        this.id = id;
        this.code = code;
        this.deviceId = deviceId;
        this.userId = userId;
        this.rfid = rfid;
        this.dishId = dishId;
        this.price = price;
        this.amount = amount;
        this.money = money;
        this.createDate = createDate;
        this.tradeDate = tradeDate;
        this.isUpload = isUpload;
        this.updoadDate = updoadDate;
        this.leftAmount = leftAmount;
        this.deviceCode = deviceCode;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", deviceId=" + deviceId +
                ", userId=" + userId +
                ", rfid='" + rfid + '\'' +
                ", dishId=" + dishId +
                ", price=" + price +
                ", amount=" + amount +
                ", money=" + money +
                ", createDate=" + createDate +
                ", tradeDate=" + tradeDate +
                ", isUpload=" + isUpload +
                ", updoadDate=" + updoadDate +
                ", leftAmount=" + leftAmount +
                ", deviceCode='" + deviceCode + '\'' +
                '}';
    }


//    getter  setter

    /**
     * 获取
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getDeviceId() {
        return this.deviceId;
    }

    /**
     * 设置
     *
     * @param deviceId
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getRfid() {
        return this.rfid;
    }

    /**
     * 设置
     *
     * @param rfid
     */
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getDishId() {
        return this.dishId;
    }

    /**
     * 设置
     *
     * @param dishId
     */
    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /**
     * 获取每100克的价格，元为单位
     *
     * @return 每100克的价格
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置每100克的价格，元为单位
     *
     * @param price 每100克的价格，元为单位
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取重量
     *
     * @return 重量
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * 设置重量
     *
     * @param amount 重量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取金额
     *
     * @return 金额
     */
    public BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置
     *
     * @param createDate
     */
    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取交易日期
     *
     * @return 交易日期
     */
    public Integer getTradeDate() {
        return this.tradeDate;
    }

    /**
     * 设置交易日期
     *
     * @param tradeDate 交易日期
     */
    public void setTradeDate(Integer tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 获取是否已上传到线上服务器
     *
     * @return 是否已上传到线上服务器
     */
    public Integer getIsUpload() {
        return this.isUpload;
    }

    /**
     * 设置是否已上传到线上服务器
     *
     * @param isUpload 是否已上传到线上服务器
     */
    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getUpdoadDate() {
        return this.updoadDate;
    }

    /**
     * 设置
     *
     * @param updoadDate
     */
    public void setUpdoadDate(Integer updoadDate) {
        this.updoadDate = updoadDate;
    }

    /**
     * 获取菜盘剩余量
     *
     * @return 菜盘剩余量
     */
    public Integer getLeftAmount() {
        return this.leftAmount;
    }

    /**
     * 设置菜盘剩余量
     *
     * @param leftAmount 菜盘剩余量
     */
    public void setLeftAmount(Integer leftAmount) {
        this.leftAmount = leftAmount;
    }

    /**
     * 获取设备编码
     *
     * @return 设备编码
     */
    public String getDeviceCode() {
        return this.deviceCode;
    }

    /**
     * 设置设备编码
     *
     * @param deviceCode 设备编码
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
