package com.zjxl.yanj.padtest.Model.SettingsModel.Business;

import android.os.Handler;
import android.os.Message;

import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Model.DAO.LineDAO;
import com.zjxl.yanj.padtest.Utils.ThreadPool_Util;

/**
 * 类名: SettingsBusiness_AddLine <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:21 <p>
 * 描述:  设置模块——新增设备（餐线、餐眼）
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsBusiness_AddLine {

//    ------------------------------------新增设备(Line/Hole)👇-------------------------------------------

    private OnAddLineLisener onAddLineLisener;

    /**
     * 新增餐线
     * 需要检验line是否已存在数据库
     *
     * @param line
     */
    public void addLine(final Line line) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                LineDAO lineDAO = new LineDAO();
                int flag = lineDAO.addLine(line);
                handler_AddLine.sendEmptyMessage(flag);

            }
        });
    }


    /**
     * 消息处理————添加餐线
     */
    private Handler handler_AddLine = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LineDAO.FLAG_ERROR:
                    onAddLineLisener.error();
                    break;
                case LineDAO.FLAG_SUCCESS:
                onAddLineLisener.success();
                break;
                case LineDAO.FLAG_EXIST_LINE_NAME:
                    onAddLineLisener.exist();
                    break;
            }
        }
    };

    /**
     * 设置监听器
     * 新增设备的回调
     * @param onAddLineLisener
     */
    public void setOnAddLineLisener(OnAddLineLisener onAddLineLisener) {
        if (null != onAddLineLisener) {
            this.onAddLineLisener = onAddLineLisener;
        } else {
            System.out.println("aaa onAddLineLisener is null");
        }
    }

    /**
     * 监听器
     * 新增设备时，是否成功
     */
    public interface OnAddLineLisener {
        //        报错
        void error();
        //        已存在
        void exist();
        //        成功
        void success();
    }

//    ------------------------------------新增餐眼Hole👆-------------------------------------------

}