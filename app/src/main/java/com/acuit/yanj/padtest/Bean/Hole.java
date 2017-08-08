package com.acuit.yanj.padtest.Bean;

import java.io.Serializable;

/**
 * 类名: Hole <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/27 15:00 <p>
 * 描述:  餐眼表
 * <p>
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 * `uuid` varchar(32) DEFAULT NULL,
 * `password` varchar(32) DEFAULT NULL,
 * `statu` tinyint(4) DEFAULT '0' COMMENT '-2,离线，0，离线，1，在线。',
 * `lastloginip` varchar(15) DEFAULT NULL,
 * `lastlogintime` int(11) DEFAULT '0',
 * `socket_fd` int(11) DEFAULT '0',
 * `lineId` tinyint(4) DEFAULT '0' COMMENT '第几组',
 * `num` tinyint(4) DEFAULT '0' COMMENT '组内的号',
 * `dep_id` int(11) DEFAULT '0' COMMENT '部门id',
 * `last_live_time` int(11) DEFAULT '0' COMMENT '最后心跳时间',
 * `on_line` tinyint(4) DEFAULT '0' COMMENT '0,离线，1，在线。',
 * `name` varchar(24) DEFAULT '' COMMENT '餐眼名称',
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class Hole implements Serializable {

    private static final long serialVersionUID = -8301936995906793905L;

    /** 数据库记录id */
    private int id;

    /** 餐眼设备编号 */
    private String uuid;

    /**  */
    private String password;

    /** -2,离线，0，离线，1，在线 */
    private Integer statu;

    /** 最后访问ip */
    private String lastloginip;

    /** 最后访问时间 */
    private Integer lastlogintime;

    /**  */
    private Integer socketFd;

    /**
     * 第几组——所属餐线（Line.id）
     */
    private Integer lineId;

    /**
     * 组内的号——餐线内第几个眼
     */
    private Integer num;

    /** 部门id ——所属餐厅、食堂（）*/
    private Integer depId;

    /**
     * 最后心跳时间
     */
    private Integer lastLiveTime;

    /**
     * 0,离线，1，在线。
     */
    private Integer onLine;

    /**
     * 餐眼名称
     */
    private String name;


    public Hole() {
    }

    public Hole(int id, String uuid, String password, Integer statu, String lastloginip, Integer lastlogintime, Integer socketFd, Integer lineId, Integer num, Integer depId, Integer lastLiveTime, Integer onLine, String name) {
        this.id = id;
        this.uuid = uuid;
        this.password = password;
        this.statu = statu;
        this.lastloginip = lastloginip;
        this.lastlogintime = lastlogintime;
        this.socketFd = socketFd;
        this.lineId = lineId;
        this.num = num;
        this.depId = depId;
        this.lastLiveTime = lastLiveTime;
        this.onLine = onLine;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hole{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", password='" + password + '\'' +
                ", statu=" + statu +
                ", lastloginip='" + lastloginip + '\'' +
                ", lastlogintime=" + lastlogintime +
                ", socketFd=" + socketFd +
                ", lineId=" + lineId +
                ", num=" + num +
                ", depId=" + depId +
                ", lastLiveTime=" + lastLiveTime +
                ", onLine=" + onLine +
                ", name='" + name + '\'' +
                '}';
    }


    //    setter getter

    /**
     * 获取
     *
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取-2,离线，0，离线，1，在线。
     *
     * @return -2,离线
     */
    public Integer getStatu() {
        return this.statu;
    }

    /**
     * 设置-2,离线，0，离线，1，在线。
     *
     * @param statu -2,离线，0，离线，1，在线。
     */
    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getLastloginip() {
        return this.lastloginip;
    }

    /**
     * 设置
     *
     * @param lastloginip
     */
    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getLastlogintime() {
        return this.lastlogintime;
    }

    /**
     * 设置
     *
     * @param lastlogintime
     */
    public void setLastlogintime(Integer lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getSocketFd() {
        return this.socketFd;
    }

    /**
     * 设置
     *
     * @param socketFd
     */
    public void setSocketFd(Integer socketFd) {
        this.socketFd = socketFd;
    }

    /**
     * 获取第几组
     *
     * @return 第几组
     */
    public Integer getLineId() {
        return this.lineId;
    }

    /**
     * 设置第几组
     *
     * @param lineId 第几组
     */
    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取组内的号
     *
     * @return 组内的号
     */
    public Integer getNum() {
        return this.num;
    }

    /**
     * 设置组内的号
     *
     * @param num 组内的号
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取部门id
     *
     * @return 部门id
     */
    public Integer getDepId() {
        return this.depId;
    }

    /**
     * 设置部门id
     *
     * @param depId 部门id
     */
    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    /**
     * 获取最后心跳时间
     *
     * @return 最后心跳时间
     */
    public Integer getLastLiveTime() {
        return this.lastLiveTime;
    }

    /**
     * 设置最后心跳时间
     *
     * @param lastLiveTime 最后心跳时间
     */
    public void setLastLiveTime(Integer lastLiveTime) {
        this.lastLiveTime = lastLiveTime;
    }

    /**
     * 获取0,离线，1，在线。
     *
     * @return 0, 离线
     */
    public Integer getOnLine() {
        return this.onLine;
    }

    /**
     * 设置0,离线，1，在线。
     *
     * @param onLine 0,离线，1，在线。
     */
    public void setOnLine(Integer onLine) {
        this.onLine = onLine;
    }

    /**
     * 获取餐眼名称
     *
     * @return 餐眼名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置餐眼名称
     *
     * @param name 餐眼名称
     */
    public void setName(String name) {
        this.name = name;
    }
}