package com.zjxl.yanj.padtest.Bean;

import org.litepal.crud.DataSupport;

/**
 * 类名: Dish <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/14 18:59 <p>
 * 描述:
 *
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */




public class Dish extends DataSupport {

    /**
     * amount : 2
     * cate : 16
     * cate_name : 卤味类
     * id : 1948
     * name : 浙大烤鸡（kg）
     * no :
     * pic : http://smart.zjulab.com/upload/stock/show/1428943879156.jpg
     * price : 50.00
     * stock_id : 1948
     * sell_100gram_price : 0
     */

    private int id;
    private String stock_id;
    private String name;
    private int amount;
    private String price;
    private int sell_100gram_price;
    private String pic;
    private String cate;
    private String cate_name;
    private String no;

    public Dish() {
    }

    public Dish(int id, String stock_id, String name, int amount, String price, int sell_100gram_price, String pic, String cate, String cate_name, String no) {
        this.id = id;
        this.stock_id = stock_id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.sell_100gram_price = sell_100gram_price;
        this.pic = pic;
        this.cate = cate;
        this.cate_name = cate_name;
        this.no = no;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", stock_id='" + stock_id + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price='" + price + '\'' +
                ", sell_100gram_price=" + sell_100gram_price +
                ", pic='" + pic + '\'' +
                ", cate='" + cate + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", no='" + no + '\'' +
                '}';
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public int getSell_100gram_price() {
        return sell_100gram_price;
    }

    public void setSell_100gram_price(int sell_100gram_price) {
        this.sell_100gram_price = sell_100gram_price;
    }
}
