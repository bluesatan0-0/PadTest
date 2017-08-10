package com.acuit.yanj.padtest.Model.EditBusiness;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;

import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.DAO.DishDAO;
import com.acuit.yanj.padtest.Model.DAO.HoleDAO;
import com.acuit.yanj.padtest.Model.DAO.LineDAO;
import com.acuit.yanj.padtest.Model.DAO.PlateDAO;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

import java.util.ArrayList;
import java.util.List;

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

public class EditBusiness_DataLoad {

    //    ------------------------------------获取集合(餐线餐眼，排菜)?👇-------------------------------------------

    private OnDataLoadedLisener onDataLoadedLisener;
    private OnSaveDishesListener onSaveDishesListener;


    public static final int FLAGE_SAVE_DISHES = 1;
    public static final int LOADED_HOLES_BY_NAME = 2;
    public static final int LOADED_LINES_HOLES_PLATES_DISHES = 3;
    public static final int LOADED_DISHES = 0;


    /**
     * 消息处理————获取集合(餐线，餐眼，排菜)
     */
    private Handler handler_GetList = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

//                餐线+餐眼 加载完成
                case LOADED_LINES_HOLES_PLATES_DISHES:

                    Object[] datas = (Object[]) msg.obj;
                    List<Line> linesList = (List<Line>) datas[0];
                    List<Hole> holesList = (List<Hole>) datas[1];
                    ArrayMap<String, Plate> platesList = (ArrayMap<String, Plate>) datas[2];
                    List<Dish> dishesList = (List<Dish>) datas[3];

                    if ((null != linesList) && (null != holesList) && (null != platesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Plates_Dishes(linesList, holesList, platesList, dishesList);
                    } else {
                        System.out.println("aaa 排菜餐眼查询结果集为空");
                    }
                    break;
                case LOADED_HOLES_BY_NAME:

                    holesList = (ArrayList<Hole>) msg.obj;

                    if ((null != holesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Plates_Dishes(null, holesList, null, null);
                    } else {
                        System.out.println("aaa 排菜餐眼查询结果集为空");
                    }
                    break;
                case LOADED_DISHES:

                    dishesList = (ArrayList<Dish>) msg.obj;

                    if ((null != dishesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Plates_Dishes(null, null, null, dishesList);
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
     * 获取餐线+餐眼+排菜+菜单
     */
    public void getList_Lines_Holes_Plates() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                Object[] dataLists = new Object[4];

                LineDAO lineDAO = new LineDAO();
                dataLists[0] = lineDAO.getAllLines();

                HoleDAO holeDAO = new HoleDAO();
                dataLists[1] = holeDAO.getAllHoles();

                PlateDAO plateDAO = new PlateDAO();
                dataLists[2] = plateDAO.getPlates();

                DishDAO dishDAO = new DishDAO();
                dataLists[3] = dishDAO.getLastDishes();

                Message msg = Message.obtain();
                msg.obj = dataLists;
                msg.what = LOADED_LINES_HOLES_PLATES_DISHES;
                handler_GetList.sendMessage(msg);
            }
        });
    }

    /**
     * 通过 餐线名
     * 获取  某条餐线  排菜成功的  餐眼集合plates
     *
     * @param lineName
     */
    public void getList_HolesByLineName(final String lineName) {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                HoleDAO holeDAO = new HoleDAO();
                Message msg = Message.obtain();
                msg.obj = holeDAO.getHolesByLineName(lineName);
                msg.what = LOADED_HOLES_BY_NAME;
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
     * 只查询 最新一次下载的dishes集合
     */
    public void getList_Dishes() {
        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                Message msg = Message.obtain();
                msg.what = LOADED_DISHES;
                DishDAO dishDAO = new DishDAO();
                msg.obj = dishDAO.getLastDishes();
                handler_GetList.sendMessage(msg);
            }
        });

    }

    /**
     * 监听器
     * 餐线加载完成时回调linesLoaded(List<line> lines)函数
     */
    public interface OnDataLoadedLisener {

        //        餐线+餐眼+排菜  完成加载
        void load_Lines_Holes_Plates_Dishes(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishList);
    }

//    ------------------------------------获取集合(餐线+餐眼+排菜)👆-------------------------------------------


//    ------------------------------------保存今日菜单(dishes)👇?-------------------------------------------


    /**
     * 消息处理————根据数据库操作结果（保存菜单），回调接口
     */
    private Handler handler_SaveDishes = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//               排菜 储存完成
                case FLAGE_SAVE_DISHES:
                    if (1 == msg.arg1) {
                        onSaveDishesListener.success();
                    } else {
                        onSaveDishesListener.error();
                    }
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 保存 下载的菜单 操作结果，回调
     */
    public interface OnSaveDishesListener {

        void success();

        void error();
    }

    public void setOnSaveDishesListener(OnSaveDishesListener onSaveDishesListener) {
        this.onSaveDishesListener = onSaveDishesListener;
    }

    /**
     * 储存下载的今日菜单
     *
     * @param dishes 排菜信息集合
     */
    public void saveDishes(final ArrayList<Dish> dishes) {
        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                DishDAO dishDAO = new DishDAO();
                boolean result = dishDAO.save(dishes);

                Message msg = Message.obtain();
                msg.what = FLAGE_SAVE_DISHES;
                if (result) {
                    msg.arg1 = 1;
                } else {
                    msg.arg1 = 0;
                }
                handler_SaveDishes.sendMessage(msg);
            }
        });
    }

//    ------------------------------------保存今日菜单(dishes)👆-------------------------------------------

}