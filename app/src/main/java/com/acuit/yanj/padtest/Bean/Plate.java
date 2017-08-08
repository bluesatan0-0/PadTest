package com.acuit.yanj.padtest.Bean;

import java.math.BigDecimal;

/**
 * 类名: Plate <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/8/7 13:47 <p>
 * 描述:
 * 排菜表(MENU_ORDER)
 * version 1.0.0 2017-08-07
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class Plate implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -785397476830510443L;

    /**  */
    private String id;

    /**  */
    private Integer device_id;

    /**  */
    private String device_code;

    /** stock_id */
    private Integer dish_id;

    /**  */
    private String dish_code;

    /**  */
    private String dish_name;

    /** 每100克的价格，元为单位 */
    private BigDecimal price;

    /**  */
    private Integer create_date;

    /** 0刚排菜，1，排菜成功,2,历史排菜，-1，排菜失败 */
    private Integer status;

    /** 剩余数量 */
    private BigDecimal left_amount;

    /** 千卡 */
    private BigDecimal kcal;

    /**  */
    private BigDecimal kcal_nrv;

    /** 图片url */
    private String menu_url;


    public Plate() {
    }

    public Plate(String id, Integer device_id, String device_code, Integer dish_id, String dish_code, String dish_name, BigDecimal price, Integer create_date, Integer status, BigDecimal left_amount, BigDecimal kcal, BigDecimal kcal_nrv, String menu_url) {
        this.id = id;
        this.device_id = device_id;
        this.device_code = device_code;
        this.dish_id = dish_id;
        this.dish_code = dish_code;
        this.dish_name = dish_name;
        this.price = price;
        this.create_date = create_date;
        this.status = status;
        this.left_amount = left_amount;
        this.kcal = kcal;
        this.kcal_nrv = kcal_nrv;
        this.menu_url = menu_url;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "id='" + id + '\'' +
                ", device_id=" + device_id +
                ", device_code='" + device_code + '\'' +
                ", dish_id=" + dish_id +
                ", dish_code='" + dish_code + '\'' +
                ", dish_name='" + dish_name + '\'' +
                ", price=" + price +
                ", create_date=" + create_date +
                ", status=" + status +
                ", left_amount=" + left_amount +
                ", kcal=" + kcal +
                ", kcal_nrv=" + kcal_nrv +
                ", menu_url='" + menu_url + '\'' +
                '}';
    }




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
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getDevice_id() {
        return this.device_id;
    }

    /**
     * 设置
     *
     * @param device_id
     *
     */
    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDevice_code() {
        return this.device_code;
    }

    /**
     * 设置
     *
     * @param device_code
     *
     */
    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    /**
     * 获取stock_id
     *
     * @return stock_id
     */
    public Integer getDish_id() {
        return this.dish_id;
    }

    /**
     * 设置stock_id
     *
     * @param dish_id
     *          stock_id
     */
    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDish_code() {
        return this.dish_code;
    }

    /**
     * 设置
     *
     * @param dish_code
     *
     */
    public void setDish_code(String dish_code) {
        this.dish_code = dish_code;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDish_name() {
        return this.dish_name;
    }

    /**
     * 设置
     *
     * @param dish_name
     *
     */
    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
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
     * @param price
     *          每100克的价格，元为单位
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getCreate_date() {
        return this.create_date;
    }

    /**
     * 设置
     *
     * @param create_date
     *
     */
    public void setCreate_date(Integer create_date) {
        this.create_date = create_date;
    }

    /**
     * 获取0刚排菜，1，排菜成功,2,历史排菜，-1，排菜失败
     *
     * @return 0刚排菜
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置0刚排菜，1，排菜成功,2,历史排菜，-1，排菜失败
     *
     * @param status
     *          0刚排菜，1，排菜成功,2,历史排菜，-1，排菜失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取剩余数量
     *
     * @return 剩余数量
     */
    public BigDecimal getLeft_amount() {
        return this.left_amount;
    }

    /**
     * 设置剩余数量
     *
     * @param left_amount
     *          剩余数量
     */
    public void setLeft_amount(BigDecimal left_amount) {
        this.left_amount = left_amount;
    }

    /**
     * 获取千卡
     *
     * @return 千卡
     */
    public BigDecimal getKcal() {
        return this.kcal;
    }

    /**
     * 设置千卡
     *
     * @param kcal
     *          千卡
     */
    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    /**
     * 获取
     *
     * @return
     */
    public BigDecimal getKcal_nrv() {
        return this.kcal_nrv;
    }

    /**
     * 设置
     *
     * @param kcal_nrv
     *
     */
    public void setKcal_nrv(BigDecimal kcal_nrv) {
        this.kcal_nrv = kcal_nrv;
    }

    /**
     * 获取图片url
     *
     * @return 图片url
     */
    public String getMenu_url() {
        return this.menu_url;
    }

    /**
     * 设置图片url
     *
     * @param menu_url
     *          图片url
     */
    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }
}