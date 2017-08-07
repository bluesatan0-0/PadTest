package com.zjxl.yanj.padtest.Model.MainModel.Business;

import android.os.Handler;
import android.os.Message;

import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Bean.Plate;
import com.zjxl.yanj.padtest.Model.DAO.HoleDAO;
import com.zjxl.yanj.padtest.Model.DAO.LineDAO;
import com.zjxl.yanj.padtest.Model.DAO.PlateDAO;
import com.zjxl.yanj.padtest.Utils.ThreadPool_Util;

import java.util.List;

import static com.zjxl.yanj.padtest.Model.SettingsModel.Business.SettingsBusiness_DataLoad.LOADED_LINES;

/**
 * 类名: MainBusiness_DataLoad <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/8/7 12:13 <p>
 * 描述: 主页模块——数据获取
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class MainBusiness_DataLoad {

    //    ------------------------------------获取集合(餐线餐眼，排菜)?👇-------------------------------------------

    private OnDataLoadedLisener onDataLoadedLisener;

    public static final int LOADED_LINES_HOLES_PLATES = 3;

    /**
     * 消息处理————获取集合(餐线，餐眼，排菜)
     */
    private Handler handler_GetList = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

//                餐线+餐眼 加载完成
                case LOADED_LINES_HOLES_PLATES:

                    Object[] datas = (Object[]) msg.obj;
                    List<Line> linesList = (List<Line>) datas[0];
                    List<Hole> holesList = (List<Hole>) datas[1];
                    List<Plate> platesList = (List<Plate>) datas[2];

                    if ((null != linesList) && (null != holesList) && (null != platesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Dishes(linesList, holesList, platesList);
                    } else {
                        System.out.println("aaa 排菜餐眼查询结果集为空");
                    }
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 获取餐线+餐眼+排菜
     */
    public void getList_Lines_Holes_Plates() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                Object[] dataLists = new Object[3];

                LineDAO lineDAO = new LineDAO();
                dataLists[0] = lineDAO.getAllLines();

                HoleDAO holeDAO = new HoleDAO();
                dataLists[1] = holeDAO.getAllHoles();

                PlateDAO plateDAO = new PlateDAO();
                dataLists[2] = plateDAO.getPlates();

                Message msg = Message.obtain();
                msg.obj = dataLists;
                msg.what = LOADED_LINES_HOLES_PLATES;
                handler_GetList.sendMessage(msg);
            }
        });
    }

    /**
     * 获取排菜餐眼集合plates
     */
    public void getList_PlatesByLineName(final String lineName) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                PlateDAO plateDAO = new PlateDAO();
                Message msg = Message.obtain();
                msg.obj = plateDAO.getPlatesByLineName(lineName);
                msg.what = LOADED_LINES;
                handler_GetList.sendMessage(msg);
            }
        });
    }


    /**
     * 设置监听器（餐线加载完成）
     */
    public void setOnDataLoadedLisener(OnDataLoadedLisener onDataLoadedLisener) {
        if (null != onDataLoadedLisener) {
            this.onDataLoadedLisener = onDataLoadedLisener;
        } else {
            System.out.println("aaa onDataLoadedLisener is null");
        }
    }

    /**
     * 监听器
     * 餐线加载完成时回调linesLoaded(List<line> lines)函数
     */
    public interface OnDataLoadedLisener {

        //        餐线+餐眼+排菜  完成加载
        void load_Lines_Holes_Dishes(List<Line> linesList, List<Hole> holesList, List<Plate> plateList);
    }

//    ------------------------------------获取集合(餐线+餐眼+排菜)👆-------------------------------------------

}