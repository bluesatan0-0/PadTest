package com.acuit.yanj.padtest.Bean;

import java.io.Serializable;

/**
 * 类名: Line <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 12:21 <p>
 * 描述: 餐线bean
 * CREATE TABLE `menu_rows` (
 * `id` int(6) unsigned NOT NULL AUTO_INCREMENT,
 * `code` varchar(24) DEFAULT NULL,
 * `name` varchar(24) DEFAULT NULL,
 * `status` tinyint(4) DEFAULT '1' COMMENT '0，不可用，1，可用。',
 * PRIMARY KEY (`id`),
 * UNIQUE KEY `uk_code` (`code`) USING BTREE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='餐线表';
 * <p>
 * <p>
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class Line implements Serializable {

    private static final long serialVersionUID = 6303581569693408360L;

    private int id;
    private String code;
    private String name;
    private int status;

    public Line() {
    }


    /**
     * 根据餐线名称生成餐线实例
     * @param name 餐线名称
     */
    public Line(String name) {
        this.name = name;
    }

    public Line(int id, String code, String name, int status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
