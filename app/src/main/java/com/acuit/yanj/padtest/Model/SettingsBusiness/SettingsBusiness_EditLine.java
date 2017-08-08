package com.acuit.yanj.padtest.Model.SettingsBusiness;

import android.os.Handler;
import android.os.Message;

import com.acuit.yanj.padtest.Model.DAO.LineDAO;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

/**
 * 类名: SettingsBusiness_EditLine <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:21 <p>
 * 描述:  设置模块——编辑餐线
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsBusiness_EditLine {

//    ------------------------------------编辑餐线👇-------------------------------------------

    private OnEditLineLisener onEditLineLisener;

    /**
     * 编辑餐线
     * 检测新餐线名称是否已存在
     *
     * @param lineName 被修改的餐线名称
     * @param newName  新餐线名称
     */
    public void editLine(final String lineName, final String newName) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                LineDAO lineDAO = new LineDAO();
                int flag = lineDAO.editLine(lineName, newName);
                handler_EditLine.sendEmptyMessage(flag);

            }
        });
    }


    /**
     * 消息处理————编辑餐线
     */
    private Handler handler_EditLine = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LineDAO.FLAG_ERROR:
                    onEditLineLisener.error();
                    break;
                case LineDAO.FLAG_SUCCESS:
                    onEditLineLisener.success();
                    break;
                case LineDAO.FLAG_EXIST_LINE_NAME:
                    onEditLineLisener.exist();
                    break;
            }
        }
    };

    /**
     * 设置监听器
     * 编辑餐线的回调
     *
     * @param onEditLineLisener
     */
    public void setOnEditLineLisener(OnEditLineLisener onEditLineLisener) {
        if (null != onEditLineLisener) {
            this.onEditLineLisener = onEditLineLisener;
        } else {
            System.out.println("aaa onEditLineLisener is null");
        }
    }

    /**
     * 监听器
     * 编辑餐线时，是否成功
     */
    public interface OnEditLineLisener {
        //        报错
        void error();

        //        已存在
        void exist();

        //        成功
        void success();
    }

//    ------------------------------------编辑餐线👆-------------------------------------------

}