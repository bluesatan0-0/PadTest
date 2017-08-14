package com.acuit.yanj.padtest.Model.MainBusiness;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.widget.Toast;

import com.acuit.yanj.padtest.Base.BaseApplication;
import com.acuit.yanj.padtest.Base.BaseArrayMap;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.MenuList;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.DAO.DishDAO;
import com.acuit.yanj.padtest.Model.DAO.HoleDAO;
import com.acuit.yanj.padtest.Model.DAO.LineDAO;
import com.acuit.yanj.padtest.Model.DAO.PlateDAO;
import com.acuit.yanj.padtest.Utils.SharedPreference_Utils;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;
import com.acuit.yanj.padtest.Utils.URL_Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class MainBusiness_DataLoad {


    private OnDataLoadedLisener onDataLoadedLisener;
    private OnUpdateListener onUpdateListener;
    private OnDownloadListener onDownloadListener;


    public static final int FLAG_DOWNLOAD_MENU = 0;
    public static final int FLAGE_UPDATE_PLATES = 1;
    public static final int LOADED_HOLES_BY_NAME = 2;
    public static final int LOADED_LINES_HOLES_PLATES = 3;

    //    ------------------------------------获取集合(餐线餐眼，排菜)?👇-------------------------------------------
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
                    ArrayMap<String, Plate> platesList = (ArrayMap<String, Plate>) datas[2];
                    List<Dish> dishesList = (List<Dish>) datas[3];

                    if ((null != linesList) && (null != holesList) && (null != platesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Plates(linesList, holesList, platesList, dishesList);
                    } else {
                        System.out.println("aaa 排菜餐眼查询结果集为空");
                    }
                    break;
                case LOADED_HOLES_BY_NAME:

                    holesList = (ArrayList<Hole>) msg.obj;

                    if ((null != holesList)) {
                        onDataLoadedLisener.load_Lines_Holes_Plates(null, holesList, null, null);
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
        void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishesList);
    }


    /**
     * 获取餐线+餐眼+排菜
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
                msg.what = LOADED_LINES_HOLES_PLATES;
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
    public void getList_PlatesByLineName(final String lineName) {

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


//    ------------------------------------获取集合(餐线+餐眼+排菜)👆-------------------------------------------

//    ------------------------------------更新、保存(排菜信息)👇?-------------------------------------------

    /**
     * 消息处理————获取更新结果 （排菜信息）
     */
    private Handler handler_UpdateResult = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//               排菜 储存完成
                case FLAGE_UPDATE_PLATES:
                    if (1 == msg.arg1) {
                        onUpdateListener.success();
                    } else {
                        onUpdateListener.error();
                    }
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 更新排菜信息的操作结果，回调
     */
    public interface OnUpdateListener {

        void success();

        void error();
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    /**
     * 储存排菜结果
     *
     * @param plates 排菜信息集合
     */
    public void uploadPlates(final BaseArrayMap<String, Plate> plates) {
        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {
                PlateDAO plateDAO = new PlateDAO();
                ArrayList<Plate> platesList = new ArrayList<Plate>();
                platesList.clear();
                for (String key : plates.keySet()) {
                    platesList.add(plates.get(key));
                }

                boolean result = plateDAO.update(platesList);
                Message msg = Message.obtain();
                msg.what = FLAGE_UPDATE_PLATES;
                if (result) {
                    msg.arg1 = 1;
                } else {
                    msg.arg1 = 0;
                }
                handler_UpdateResult.sendMessage(msg);
            }
        });
    }
//    ------------------------------------更新、保存(排菜信息)👆?-------------------------------------------

//    ------------------------------------下载今日菜单👇?-------------------------------------------

    /**
     * 消息处理————下载今日菜单
     */
    private Handler handler_DownloadMenu = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case FLAG_DOWNLOAD_MENU:
                    onDownloadListener.downloadMenu((ArrayList<Dish>) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 更新排菜信息的操作结果，回调
     */
    public interface OnDownloadListener {

        void downloadMenu(ArrayList<Dish> dishesList);
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }


    /**
     * 下载今日菜单
     */
    public void DownloadMenu() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                ArrayMap<String, String> configs = SharedPreference_Utils.getConfigs();
                String dishListJson_url = URL_Util.getDishListJson_URL(configs.get(SharedPreference_Utils.KEY_REMOTE_SERVER_IP), configs.get(SharedPreference_Utils.KEY_REMOTE_SERVER_DEPATEMENT_CODE));
                System.out.println("aaa 服务器url:" + dishListJson_url);

//                dishListJson_url = "http://192.168.2.241/apis.php?c=Z_braindisc&a=platesettings&shop=15&format=json&d=2017-07-15&shoptimes=2";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, dishListJson_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message msg = Message.obtain();
                        Gson gson = new Gson();
                        MenuList menuList = gson.fromJson(response, MenuList.class);
                        msg.obj = getDishesFromMenuList(menuList);
                        msg.what = FLAG_DOWNLOAD_MENU;
                        handler_DownloadMenu.sendMessage(msg);
                        Toast.makeText(BaseApplication.getInstance(), "下载今日菜单成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BaseApplication.getInstance(), "下载今日菜单失败", Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue = BaseApplication.getRequestQueue();
                requestQueue.add(stringRequest);
                stringRequest.setTag("DownloadMenu");

            }
        });
    }


    /**
     * 将下载的今日菜单 转换为 菜品list
     *
     * @param menuList
     * @return
     */
    private ArrayList<Dish> getDishesFromMenuList(MenuList menuList) {
        ArrayList<Dish> dishesList = new ArrayList<Dish>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        List<MenuList.PlanProdsBean> plan_prods = menuList.getPlan_prods();
        for (MenuList.PlanProdsBean plan_prod : plan_prods) {

            Dish dish = new Dish();

            dish.setStock_id(Integer.valueOf(plan_prod.getStock_id()));
            dish.setName(plan_prod.getName());
            dish.setPart(Integer.valueOf(menuList.getPart()));
            dish.setDate(time);
            dish.setAmount(BigDecimal.valueOf(plan_prod.getAmount()));
            dish.setCat_name(plan_prod.getCate_name());
            dish.setCate(Integer.valueOf(plan_prod.getCate()));
            dish.setPic(plan_prod.getPic());
            dish.setPrice(BigDecimal.valueOf(Double.valueOf(plan_prod.getPrice())));
            dish.setSell_100gram_price(BigDecimal.valueOf(Double.valueOf(plan_prod.getSell_100gram_price())));

            dishesList.add(dish);
        }

        return dishesList;
    }

//    ------------------------------------下载今日菜单👆?-------------------------------------------


}
