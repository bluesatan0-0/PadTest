package com.acuit.yanj.padtest.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acuit.yanj.padtest.Adapter.HolesAdapter_Main;
import com.acuit.yanj.padtest.Adapter.LinesAdapter;
import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.Base.BaseApplication;
import com.acuit.yanj.padtest.Base.BaseArrayList;
import com.acuit.yanj.padtest.Base.BaseArrayMap;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.EditBusiness.EditBusiness_DataLoad;
import com.acuit.yanj.padtest.Model.MainBusiness.MainBusiness_DataLoad;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Service.Service_refrashWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: MainActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 11:32 <p>
 * 描述: MainActivity     主页
 * <p>
 * 更新人: yanj<p>
 * 更新时间: 2017-08-07 10:56:42 <p>
 * 更新描述: 实现功能（数据库操作模块）<p>
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, ServiceConnection {

    private View btnPlanDish;
    private View btnDownloadMenu;
    //    private View btnUploadPlan;
    private View btnOrderList;
    private View btnSettings;
    private Button btnAllLines;

    private RecyclerView rvHoles;
    private RecyclerView rvLines;

    private LinesAdapter linesAdapter;
    private HolesAdapter_Main holesAdapter_main;

    private BaseArrayList<Line> lines;
    private BaseArrayList<Hole> holes;
    private BaseArrayList<Dish> dishes;
    private BaseArrayMap<String, Plate> plates;

//    private BaseArrayList<Hole> tempHoles;
//    private BaseArrayMap<String, Plate> tempPlates;

    private Context context;
    private BaseArrayList<String> invalidateHolesUuid;
    private Intent intentService;
    private Service_refrashWeight.MyBinder myService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        updateNotifyDataSet_LinesHolesPlates();


        intentService = new Intent(this, Service_refrashWeight.class);
        bindService(intentService, this, Context.BIND_AUTO_CREATE);

    }


    @Override
    public void onServiceConnected(final ComponentName name, IBinder service) {

        myService = (Service_refrashWeight.MyBinder) service;
        myService.setServiceCallBack(new Service_refrashWeight.ServiceCallBack() {
            @Override
            public void getData(ArrayMap<String, Plate> plateList) {
                plates.clear();
//                tempPlates.clear();
                plates.putAll(plateList);
//                tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);
                if (null != holesAdapter_main) {
                    comparisonPlatesDishes();
                    holesAdapter_main.notifyDataSetChanged();
//                    System.out.println(name.toString() + " is running");
                }
            }
        });

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(this);
//        避免解绑时内存泄漏
        myService = null;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == 2) {
//            plates.clear();
//            dishes.clear();
//            invalidateHolesUuid.clear();
//            plates.putAll((Map<? extends String, ? extends Plate>) data.getSerializableExtra("Plates"));
//            dishes.addAll((Collection<? extends Dish>) data.getSerializableExtra("Dishes"));
//
//            tempHoles.clear();
//            tempPlates.clear();
//            tempHoles.addAll(holes);
//            tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);
//
////            invalidateHolesUuid.addAll((Collection<? extends String>) data.getSerializableExtra("invalidateHolesUuid"));
//            if (null == holesAdapter_main) {
//                holesAdapter_main = new HolesAdapter_Main(context, tempHoles, lines, tempPlates, invalidateHolesUuid);
//                rvHoles.setAdapter(holesAdapter_main);
//            }
//            holesAdapter_main.notifyDataSetChanged();
//
//            comparisonPlatesDishes();
//        }
//    }

    private void initView() {

        btnPlanDish = findViewById(R.id.ll_planDish);
        btnDownloadMenu = findViewById(R.id.ll_downloadMenu);
//        btnUploadPlan = findViewById(R.id.ll_uploadPlan);
        btnOrderList = findViewById(R.id.ll_orderList);
        btnSettings = findViewById(R.id.ll_settings);

        btnAllLines = (Button) findViewById(R.id.btn_allLines);

        rvLines = (RecyclerView) findViewById(R.id.rv_lines);
        rvHoles = (RecyclerView) findViewById(R.id.rv_holes);
    }

    private void initData() {
        context = this;

        lines = new BaseArrayList<Line>();
        holes = new BaseArrayList<Hole>();
        dishes = new BaseArrayList<Dish>();
        plates = new BaseArrayMap<String, Plate>();
        invalidateHolesUuid = new BaseArrayList<String>();

//        tempHoles = new BaseArrayList<Hole>();
//        tempPlates = new BaseArrayMap<String, Plate>();

        LinearLayoutManager linesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLines.setLayoutManager(linesLayoutManager);

        GridLayoutManager holesLayoutManager = new GridLayoutManager(this, 4);
        rvHoles.setLayoutManager(holesLayoutManager);

        rvLines.setHasFixedSize(true);
        rvHoles.setHasFixedSize(true);

        updateNotifyDataSet_LinesHolesPlates();
    }

    private void updateNotifyDataSet_LinesHolesPlates() {

        MainBusiness_DataLoad mainBusiness_dataLoad = new MainBusiness_DataLoad();
        mainBusiness_dataLoad.setOnDataLoadedLisener(new MainBusiness_DataLoad.OnDataLoadedLisener() {

            @Override
            public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishesList) {

                lines.clear();
                holes.clear();
                plates.clear();
                dishes.clear();
                lines.addAll(linesList);
                holes.addAll(holesList);
                plates.putAll(plateList);
                dishes.addAll(dishesList);

//                tempHoles.addAll(holes);
//                tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);

//                System.out.println("aaa holesAdapter_main.linesList:" + linesList.toString());
//                System.out.println("aaa holesAdapter_main.holesList:" + holesList.toString());
//                System.out.println("aaa holesAdapter_main.plateList:" + plateList.toString());
//                System.out.println("aaa holesAdapter_main.dishesList:" + dishesList.toString());

                if (null != linesAdapter) {
                    linesAdapter.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    linesAdapter = new LinesAdapter(context, lines);
                    linesAdapter.setOnItemClickListener(new mItemClickListener_rvLines());
                    rvLines.setAdapter(linesAdapter);
                }

                if (null != holesAdapter_main) {
                    holesAdapter_main.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    holesAdapter_main = new HolesAdapter_Main(context, holes, lines, plates, invalidateHolesUuid);
                    // TODO: 2017/8/7 点击餐盘，进入该餐盘的设置模式
//                    linesAdapter.setOnItemClickListener(new mItemClickListener_rvLines());
                    rvHoles.setAdapter(holesAdapter_main);
                }

                comparisonPlatesDishes();

            }
        });

        mainBusiness_dataLoad.getList_Lines_Holes_Plates();
    }

    private void initEvent() {

        btnDownloadMenu.setOnClickListener(this);
//        btnUploadPlan.setOnClickListener(this);
        btnOrderList.setOnClickListener(this);
        btnPlanDish.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        btnAllLines.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_planDish:
                intent = new Intent(this, EditActivity.class);
                startActivity(intent);
//                finish();
//                intent.putExtra("Lines", lines);
//                intent.putExtra("Holes", holes);
//                intent.putExtra("Plates", plates);
//                intent.putExtra("Dishes", dishes);
//                intent.putExtra("invalidateHolesUuid", invalidateHolesUuid);
//                startActivityForResult(intent, 1);

                break;
            case R.id.ll_downloadMenu:
                downloadMenuList();
                break;
//            case R.id.ll_uploadPlan:
//                uploadPlatesPlan();
//                break;
            case R.id.ll_orderList:
                startActivity(new Intent(this, OrderListActivity.class));
                break;
            case R.id.ll_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_allLines:
                updateNotifyDataSet_LinesHolesPlates();
//                pickHolesByLineClick("全  部");
                break;
        }
    }


//-------------------------------------下载今日菜单👇-----------------------------------------------------

    /**
     * 点击下载今日菜单后
     */
    private void downloadMenuList() {

        MainBusiness_DataLoad mainBusiness_dataLoad = new MainBusiness_DataLoad();
        mainBusiness_dataLoad.setOnDownloadListener(new MainBusiness_DataLoad.OnDownloadListener() {
            @Override
            public void downloadMenu(ArrayList<Dish> dishesList) {
//                System.out.println("aaa 下载的菜单为：" + dishesList.toString());

//                将下载的菜单存数据库，拿出数据库的菜品来排菜;
                saveDownloadedDishes(dishesList);

            }
        });
        mainBusiness_dataLoad.DownloadMenu();
    }

    /**
     * 将下载的菜单存数据库，拿出数据库的菜品来排菜;
     *
     * @param dishesList
     */
    private void saveDownloadedDishes(ArrayList<Dish> dishesList) {

        EditBusiness_DataLoad editBusiness_dataLoad = new EditBusiness_DataLoad();
        editBusiness_dataLoad.setOnSaveDishesListener(new EditBusiness_DataLoad.OnSaveDishesListener() {
            @Override
            public void success() {
//                从数据库获取新菜单
                updateNotifyDataSet_Dishes();

            }

            @Override
            public void error() {
                Toast.makeText(context, "储存今日菜单失败", Toast.LENGTH_SHORT).show();
            }
        });

        editBusiness_dataLoad.saveDishes(dishesList);
    }

    /**
     * 从数据库获取新菜单
     */
    private void updateNotifyDataSet_Dishes() {
        EditBusiness_DataLoad editBusiness_dataLoad = new EditBusiness_DataLoad();
        editBusiness_dataLoad.setOnDataLoadedLisener(new EditBusiness_DataLoad.OnDataLoadedLisener() {
            @Override
            public void load_Lines_Holes_Plates_Dishes(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishList) {
                dishes.clear();
                dishes.addAll(dishList);

                // TODO: 2017/8/10 比对holes+plates 与dishes 不在的无效化
                comparisonPlatesDishes();


            }
        });

        editBusiness_dataLoad.getList_Dishes();
    }

    private void comparisonPlatesDishes() {
        invalidateHolesUuid.clear();
        boolean isExist = false;
        for (Plate plate : plates.values()) {
//            System.out.println("aaa comperison.排菜里的菜名:" + plate.getDish_name() + "  排菜里菜的stock_id:" + plate.getDish_id());
            String dish_id = plate.getDish_id() + "";
            isExist = false;
            for (Dish dish : dishes) {
//                System.out.println("aaa comperison.菜单里菜名:" + dish.getName() + "  菜单里菜的stock_id:" + dish.getStock_id());
                if ((dish.getStock_id() + "").equals(dish_id)) {
                    isExist = true;
                }
            }
            if (isExist == false) {
                invalidateHolesUuid.add(plate.getDevice_code());
            }
        }

//        for (String s : invalidateHolesUuid) {
//            System.out.println("aaa mainActivity invalidateUuid:" + s);
//        }

        if (null != holesAdapter_main) {
            holesAdapter_main.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {

        BaseApplication.getRequestQueue().cancelAll("DownloadMenu");
        super.onDestroy();
    }

    //-------------------------------------下载今日菜单?👆-----------------------------------------------------


    /**
     * 餐线列表点击事件  的  回调监听器
     */
    class mItemClickListener_rvLines implements LinesAdapter.ItemClickListener {

        @Override
        public void onBtnNameClick(String lineName) {

            whenBtnLineNameClick(lineName);
//2017-08-16 需求变更：页面传值作废，
//            pickHolesByLineClick(lineName);
        }

    }


//    /**
//     * 点击餐线切换相应的餐眼，弃用whenBtnLineNameClick()
//     * 避免未上传的排菜信息丢失
//     *
//     * @param lineName 点击的餐线名称
//     */
//    private void pickHolesByLineClick(String lineName) {
//        tempHoles.clear();
//        tempPlates.clear();
//
//        if (lineName.equals("全  部")) {
//            tempHoles.addAll(holes);
//            tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);
//        } else {
//
//            int lineId = -1;
//
//            for (Line line : lines) {
//                if (lineName.equals(line.getName())) {
//                    lineId = line.getId();
//                }
//            }
//
//            for (Hole hole : holes) {
//                if (hole.getLineId() == lineId) {
//                    tempHoles.add(hole);
//                }
//            }
//
//
//            for (String holeUuid : plates.keySet()) {
//                for (Hole hole : tempHoles) {
//                    if (hole.getUuid().equals(holeUuid)) {
//                        tempPlates.put(holeUuid, plates.get(holeUuid));
//                    }
//                }
//            }
//        }
//        holesAdapter_main.notifyDataSetChanged();
//    }

    /**
     * 根据点击的line  更新rvHoles中的数据（换成被点击餐线的餐眼）
     * 当rvLines的item中btnName被点击时，执行
     *
     * @param lineName 餐线名称
     */
    private void whenBtnLineNameClick(String lineName) {
        MainBusiness_DataLoad mainBusiness_dataLoad = new MainBusiness_DataLoad();
        mainBusiness_dataLoad.setOnDataLoadedLisener(new MainBusiness_DataLoad.OnDataLoadedLisener() {

            @Override
            public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishes) {
                // TODO: 2017/8/7 餐线点击后，下载完成该餐线的餐盘信息，需完成：切换餐盘列表
//                System.out.println("aaa 点击了餐线 holes:" + holesList.toString());
                holes.clear();
                holes.addAll(holesList);
                holesAdapter_main.notifyDataSetChanged();

            }
        });
        mainBusiness_dataLoad.getList_PlatesByLineName(lineName);
    }

}
