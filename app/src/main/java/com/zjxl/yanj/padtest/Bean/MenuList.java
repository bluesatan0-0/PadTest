package com.zjxl.yanj.padtest.Bean;

import java.util.List;

/**
 * 类名: MenuList <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 14:22 <p>
 * 描述: 菜品清单数据对象
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class MenuList {

    /**
     * code : 1
     * part : 2
     * plan_prods : [{"amount":4,"cate":"16","cate_name":"卤味类","id":"2003","name":"白灼白虾(kg)","no":"","pic":"http://smart.zjulab.com/skin/images/no_cai_pic.jpg","price":"66.00","stock_id":"2003"},{"amount":2,"cate":"16","cate_name":"卤味类","id":"1948","name":"浙大烤鸡（kg）","no":"","pic":"http://smart.zjulab.com/upload/stock/show/1428943879156.jpg","price":"50.00","stock_id":"1948"}]
     * prods : []
     * sub_code : 0
     */

    private int code;
    private String part;
    private String sub_code;
    private List<PlanProdsBean> plan_prods;
    private List<?> prods;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public List<PlanProdsBean> getPlan_prods() {
        return plan_prods;
    }

    public void setPlan_prods(List<PlanProdsBean> plan_prods) {
        this.plan_prods = plan_prods;
    }

    public List<?> getProds() {
        return prods;
    }

    public void setProds(List<?> prods) {
        this.prods = prods;
    }

    public static class PlanProdsBean {
        /**
         * amount : 4
         * cate : 16
         * cate_name : 卤味类
         * id : 2003
         * name : 白灼白虾(kg)
         * no :
         * pic : http://smart.zjulab.com/skin/images/no_cai_pic.jpg
         * price : 66.00
         * stock_id : 2003
         */

        private int amount;
        private String cate;
        private String cate_name;
        private String id;
        private String name;
        private String no;
        private String pic;
        private String price;
        private String stock_id;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
    }
}