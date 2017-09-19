package com.acuit.yanj.padtest.Bean;

import java.math.BigDecimal;

/**
 * 类名: Dish <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/14 18:59 <p>
 * 描述:
 * 菜单表(MENU_PLAN_STOCK)
 * version 1.0.0 2017-08-07
 * <p>
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */


public class Dish implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -270905260069975397L;

    /**  */
    private String id;

    /**  */
    private Integer stock_id;

    /**  */
    private BigDecimal price;

    /**  */
    private double sell_100gram_price;

    /** 计划量分或者千克的单位 */
    private BigDecimal amount;

    /** 图片 */
    private String pic;

    /**  */
    private Integer cate;

    /** 图片 */
    private String cat_name;

    /** 日期 */
    private String date;

    /** 餐别：1早餐，2午餐，3晚餐，4夜宵，5其他 */
    private Integer part;

    /** 产品名 */
    private String name;


    public Dish() {
    }

    public Dish(String id, Integer stock_id, BigDecimal price, double sell_100gram_price, BigDecimal amount, String pic, Integer cate, String cat_name, String date, Integer part, String name) {
        this.id = id;
        this.stock_id = stock_id;
        this.price = price;
        this.sell_100gram_price = sell_100gram_price;
        this.amount = amount;
        this.pic = pic;
        this.cate = cate;
        this.cat_name = cat_name;
        this.date = date;
        this.part = part;
        this.name = name;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", stock_id=" + stock_id +
                ", price=" + price +
                ", sell_100gram_price=" + sell_100gram_price +
                ", amount=" + amount +
                ", pic='" + pic + '\'' +
                ", cate=" + cate +
                ", cat_name='" + cat_name + '\'' +
                ", date='" + date + '\'' +
                ", part=" + part +
                ", name='" + name + '\'' +
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
    public Integer getStock_id() {
        return this.stock_id;
    }

    /**
     * 设置
     *
     * @param stock_id
     *
     */
    public void setStock_id(Integer stock_id) {
        this.stock_id = stock_id;
    }

    /**
     * 获取
     *
     * @return
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置
     *
     * @param price
     *
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取
     *
     * @return
     */
    public double getSell_100gram_price() {
        return this.sell_100gram_price;
    }

    /**
     * 设置
     *
     * @param sell_100gram_price
     *
     */
    public void setSell_100gram_price(double sell_100gram_price) {
        this.sell_100gram_price = sell_100gram_price;
    }

    /**
     * 获取计划量分或者千克的单位
     *
     * @return 计划量分或者千克的单位
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 设置计划量分或者千克的单位
     *
     * @param amount
     *          计划量分或者千克的单位
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取图片
     *
     * @return 图片
     */
    public String getPic() {
        return this.pic;
    }

    /**
     * 设置图片
     *
     * @param pic
     *          图片
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getCate() {
        return this.cate;
    }

    /**
     * 设置
     *
     * @param cate
     *
     */
    public void setCate(Integer cate) {
        this.cate = cate;
    }

    /**
     * 获取图片
     *
     * @return 图片
     */
    public String getCat_name() {
        return this.cat_name;
    }

    /**
     * 设置图片
     *
     * @param cat_name
     *          图片
     */
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    /**
     * 获取日期
     *
     * @return 日期
     */
    public String getDate() {
        return this.date;
    }

    /**
     * 设置日期
     *
     * @param date
     *          日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取餐别：1早餐，2午餐，3晚餐，4夜宵，5其他
     *
     * @return 餐别：1早餐
     */
    public Integer getPart() {
        return this.part;
    }

    /**
     * 设置餐别：1早餐，2午餐，3晚餐，4夜宵，5其他
     *
     * @param part
     *          餐别：1早餐，2午餐，3晚餐，4夜宵，5其他
     */
    public void setPart(Integer part) {
        this.part = part;
    }

    /**
     * 获取产品名
     *
     * @return 产品名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置产品名
     *
     * @param name
     *          产品名
     */
    public void setName(String name) {
        this.name = name;
    }
}