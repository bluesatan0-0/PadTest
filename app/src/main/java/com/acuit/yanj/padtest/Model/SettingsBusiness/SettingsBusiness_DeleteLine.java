package com.acuit.yanj.padtest.Model.SettingsBusiness;

import android.os.Handler;
import android.os.Message;

import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Model.DAO.LineDAO;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

/**
 * 类名: SettingsBusiness_DeleteLine <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017-08-01 10:16:13 <p>
 * 描述:  设置模块——删除餐线
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsBusiness_DeleteLine {

//    ------------------------------------删除餐线👇-------------------------------------------

    private OnDeleteLineLisener onDeleteLineLisener;

    /**
     * 删除餐线
     * 需要同时删除所属餐眼
     *
     * @param line
     */
    public void deleteLine(final Line line) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                LineDAO lineDAO = new LineDAO();
                int flag = lineDAO.deleteLine(line);
                handler_DeleteLine.sendEmptyMessage(flag);

            }
        });
    }


    /**
     * 消息处理————删除餐线
     */
    private Handler handler_DeleteLine = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LineDAO.FLAG_ERROR:
                    onDeleteLineLisener.error();
                    break;
                case LineDAO.FLAG_SUCCESS:
                    onDeleteLineLisener.success();
                break;
            }
        }
    };

    /**
     * 设置监听器
     * 删除餐眼的回调
     * @param onDeleteLineLisener
     */
    public void setOnDeleteLineLisener(OnDeleteLineLisener onDeleteLineLisener) {
        if (null != onDeleteLineLisener) {
            this.onDeleteLineLisener = onDeleteLineLisener;
        } else {
            System.out.println("aaa onDeleteLineLisener is null");
        }
    }

    /**
     * 监听器
     * 删除餐眼时，是否成功
     */
    public interface OnDeleteLineLisener {
        //        报错
        void error();
        //        成功
        void success();
    }

//    ------------------------------------删除餐线👆-------------------------------------------

}