package com.acuit.yanj.padtest.Model.SettingsBusiness;

import android.os.Handler;
import android.os.Message;

import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Model.DAO.HoleDAO;
import com.acuit.yanj.padtest.Model.DAO.LineDAO;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

import java.util.List;

/**
 * 类名: SettingsBusiness_DataLoad <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:21 <p>
 * 描述:  设置模块——获取数据集合
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsBusiness_DataLoad {


    //    ------------------------------------获取集合(餐线餐眼)?👇-------------------------------------------

    private OnDataLoadedLisener onDataLoadedLisener;

    public static final int LOADED_LINES = 1;
    public static final int LOADED_HOLES = 2;
    public static final int LOADED_LINES_HOLES = 3;

    /**
     * 消息处理————获取集合(餐线餐眼)
     */
    private Handler handler_GetList = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                餐线集合加载完成
                case LOADED_LINES:
                    List<Line> lineList = (List<Line>) msg.obj;
                    if (null != lineList) {
                        onDataLoadedLisener.loaded_Lines(lineList);
                    } else {
                        System.out.println("aaa 餐线查询结果集为空");
                    }
                    break;
//                餐眼集合加载完成
                case LOADED_HOLES:
                    List<Hole> holeList = (List<Hole>) msg.obj;
                    if (null != holeList) {
                        onDataLoadedLisener.loaded_Holes(holeList);
                    } else {
                        System.out.println("aaa 餐线查询结果集为空");
                    }
                    break;
//                餐线+餐眼 加载完成
                case LOADED_LINES_HOLES:

                    Object[] datas = (Object[]) msg.obj;
                    List<Line> linesList = (List<Line>) datas[0];
                    List<Hole> holesList = (List<Hole>) datas[1];

                    if ((null != linesList) && (null != holesList)) {
                        onDataLoadedLisener.load_Lines_Holes(linesList, holesList);
                    } else {
                        System.out.println("aaa 餐线查询结果集为空");
                    }
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 获取餐线+餐眼两个list
     */
    public void getList_LinesAndHoles() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                Object[] dataLists = new Object[2];

                LineDAO lineDAO = new LineDAO();
                dataLists[0] = lineDAO.getAllLines();

                HoleDAO holeDAO = new HoleDAO();
                dataLists[1] = holeDAO.getAllHoles();

                Message msg = Message.obtain();
                msg.obj = dataLists;
                msg.what = LOADED_LINES_HOLES;
                handler_GetList.sendMessage(msg);
            }
        });
    }

    /**
     * 获取餐线list
     */
    public void getList_Lines() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                LineDAO lineDAO = new LineDAO();
                Message msg = Message.obtain();
                msg.obj = lineDAO.getAllLines();
                msg.what = LOADED_LINES;
                handler_GetList.sendMessage(msg);
            }
        });
    }

    /**
     * 获取 全部餐眼list
     */
    public void getList_Holes() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                HoleDAO holeDAO = new HoleDAO();
                Message msg = Message.obtain();
                msg.obj = holeDAO.getAllHoles();
                msg.what = LOADED_HOLES;
                handler_GetList.sendMessage(msg);
            }
        });
    }

    /**
     *  通过餐线名称 获取 餐眼list
     *  @param linesName 餐线名
     */
    public void getList_HolesByLinesName(final String linesName) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                HoleDAO holeDAO = new HoleDAO();
                Message msg = Message.obtain();
                msg.obj = holeDAO.getHolesByLineName(linesName);
                msg.what = LOADED_HOLES;
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

        //        餐线完成加载
        void loaded_Lines(List<Line> linesList);

        //        餐眼完成加载
        void loaded_Holes(List<Hole> holesList);

        //        餐线+餐眼完成加载
        void load_Lines_Holes(List<Line> linesList, List<Hole> holesList);
    }

//    ------------------------------------获取集合(餐线餐眼)👆-------------------------------------------

}