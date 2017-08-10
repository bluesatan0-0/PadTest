package com.acuit.yanj.padtest.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.acuit.yanj.padtest.Base.BaseArrayList;
import com.acuit.yanj.padtest.Base.BaseArrayMap;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.MenuList;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.EditBusiness.EditBusiness_DataLoad;
import com.acuit.yanj.padtest.Model.MainBusiness.MainBusiness_DataLoad;
import com.acuit.yanj.padtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View btnPlanDish;
    private View btnDownloadMenu;
    private View btnUploadPlan;
    private View btnOrderList;
    private View btnSettings;
    private RecyclerView rvHoles;
    private RecyclerView rvLines;

    private BaseArrayList<Line> lines;
    private BaseArrayList<Hole> holes;
    private BaseArrayList<Dish> dishes;
    private BaseArrayMap<String, Plate> plates;
    private MenuList menuList;
    private LinesAdapter linesAdapter;
    private HolesAdapter_Main holesAdapter_main;
    private Context context;
    private Button btnAllLines;

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            plates.clear();
            plates.putAll((Map<? extends String, ? extends Plate>) data.getSerializableExtra("Plates"));
            if (null == holesAdapter_main) {
                holesAdapter_main = new HolesAdapter_Main(context, holes, lines, plates);
                rvHoles.setAdapter(holesAdapter_main);
            }
            holesAdapter_main.notifyDataSetChanged();
        }
    }

    private void initView() {

        btnPlanDish = findViewById(R.id.ll_planDish);
        btnDownloadMenu = findViewById(R.id.ll_downloadMenu);
        btnUploadPlan = findViewById(R.id.ll_uploadPlan);
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
            public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList) {

                lines.clear();
                holes.clear();
                plates.clear();
                lines.addAll(linesList);
                holes.addAll(holesList);
                plates.putAll(plateList);
                System.out.println("aaa linesList:" + linesList.toString());
                System.out.println("aaa holesList:" + holesList.toString());
                System.out.println("aaa plateList:" + plateList.toString());

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
                    holesAdapter_main = new HolesAdapter_Main(context, holes, lines, plates);
                    // TODO: 2017/8/7 点击餐盘，进入该餐盘的设置模式
//                    linesAdapter.setOnItemClickListener(new mItemClickListener_rvLines());
                    rvHoles.setAdapter(holesAdapter_main);
                }

            }
        });

        mainBusiness_dataLoad.getList_Lines_Holes_Plates();
    }

    private void initEvent() {

        btnPlanDish.setOnClickListener(this);
        btnDownloadMenu.setOnClickListener(this);
        btnUploadPlan.setOnClickListener(this);
        btnOrderList.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        btnAllLines.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_planDish:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("Lines", lines);
                intent.putExtra("Holes", holes);
                intent.putExtra("Plates", plates);

                startActivityForResult(intent, 1);
                break;
            case R.id.ll_downloadMenu:
                downloadMenuList();
                break;
            case R.id.ll_uploadPlan:
                uploadPlatesPlan();
                break;
            case R.id.ll_orderList:

                break;
            case R.id.ll_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_allLines:
                updateNotifyDataSet_LinesHolesPlates();
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
                System.out.println("aaa 下载的菜单为：" + dishesList.toString());

//                将下载的菜单存数据库，拿出数据库的菜品来排菜;
                saveDownloadedDishes(dishesList);

            }
        });
        mainBusiness_dataLoad.DownloadMenu();
    }

    /**
     * 将下载的菜单存数据库，拿出数据库的菜品来排菜;
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



            }
        });

        editBusiness_dataLoad.getList_Dishes();
    }

//-------------------------------------下载今日菜单?👆-----------------------------------------------------



    /**
     * 将当前排菜信息存入数据库
     */
    private void uploadPlatesPlan() {
        MainBusiness_DataLoad mainBusiness_dataLoad_UP = new MainBusiness_DataLoad();
        mainBusiness_dataLoad_UP.setOnUpdateListener(new MainBusiness_DataLoad.OnUpdateListener() {
            @Override
            public void success() {
                Toast.makeText(context, "排菜上传成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error() {
                Toast.makeText(context, "上传失败！", Toast.LENGTH_SHORT).show();
            }
        });

        mainBusiness_dataLoad_UP.uploadPlates(plates);
    }


    /**
     * 餐线列表点击事件  的  回调监听器
     */
    class mItemClickListener_rvLines implements LinesAdapter.ItemClickListener {

        @Override
        public void onBtnNameClick(String lineName) {

            whenBtnLineNameClick(lineName);
        }

    }

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
            public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList) {
                // TODO: 2017/8/7 餐线点击后，下载完成该餐线的餐盘信息，需完成：切换餐盘列表
                System.out.println("aaa 点击了餐线 holes:" + holesList.toString());
                holes.clear();
                holes.addAll(holesList);
                holesAdapter_main.notifyDataSetChanged();

            }
        });
        mainBusiness_dataLoad.getList_PlatesByLineName(lineName);
    }

}
