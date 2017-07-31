package com.zjxl.yanj.padtest.Presenter;

import android.os.Handler;
import android.os.Message;

import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Model.HoleDAO;
import com.zjxl.yanj.padtest.Model.LineDAO;
import com.zjxl.yanj.padtest.Utils.ThreadPool_Util;

/**
 * 类名: SettingsPresenter_AddLine <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:21 <p>
 * 描述:  设置模块——新增设备（餐线、餐眼）
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsPresenter_AddHole {

//    ------------------------------------新增设备(Line/Hole)👇-------------------------------------------

    private OnAddHoleLisener onAddHoleLisener;

    /**
     * 新增餐眼
     * 需要检验hole是否已存在数据库
     *
     * @param hole
     */
    public void addHole(final Hole hole) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                HoleDAO holeDAO = new HoleDAO();
                int flag = holeDAO.addHole(hole);
                handler_AddHole.sendEmptyMessage(flag);

            }
        });
    }

    /**
     * 消息处理————添加餐眼
     */
    private Handler handler_AddHole = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HoleDAO.FLAG_ERROR:
                    onAddHoleLisener.error();
                    break;
                case HoleDAO.FLAG_SUCCESS:
                    onAddHoleLisener.success();
                    break;
                case HoleDAO.FLAG_EXIST_UUID:
                    onAddHoleLisener.existUuid();
                    break;
                case HoleDAO.FLAG_EXIST_ROW_NUM:
                    onAddHoleLisener.existRowNum();
                    break;
            }
        }
    };

    /**
     * 设置监听器
     * 新增餐眼的回调
     *
     * @param onAddHoleLisener
     */
    public void setOnAddHoleLisener(OnAddHoleLisener onAddHoleLisener) {
        if (null != onAddHoleLisener) {
            this.onAddHoleLisener = onAddHoleLisener;
        } else {
            System.out.println("aaa OnAddHoleLisener is null");
        }
    }

    /**
     * 监听器
     * 新增餐眼时，是否成功
     */
    public interface OnAddHoleLisener {
        //        报错
        void error();

        //        已存在
        void existUuid();

        void existRowNum();

        //        成功
        void success();
    }

//    ------------------------------------新增餐眼Hole👆-------------------------------------------

}