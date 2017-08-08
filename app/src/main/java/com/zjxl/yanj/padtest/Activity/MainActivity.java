package com.zjxl.yanj.padtest.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;

import com.zjxl.yanj.padtest.Adapter.HolesAdapter_Main;
import com.zjxl.yanj.padtest.Adapter.LinesAdapter_Main;
import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Bean.MenuList;
import com.zjxl.yanj.padtest.Bean.Plate;
import com.zjxl.yanj.padtest.Model.MainModel.Business.MainBusiness_DataLoad;
import com.zjxl.yanj.padtest.R;

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

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View btnPlanDish;
    private View btnDownloadMenu;
    private View btnUploadPlan;
    private View btnOrderList;
    private View btnSettings;
    private RecyclerView rvHoles;
    private RecyclerView rvLines;

    private List<Line> lines;
    private List<Hole> holes;
    private ArrayMap<String,Plate> plates;
    private MenuList menuList;
    private LinesAdapter_Main linesAdapter_main;
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

        lines = new ArrayList<Line>();
        holes = new ArrayList<Hole>();
        plates = new ArrayMap<String,Plate>();


        LinearLayoutManager linesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLines.setLayoutManager(linesLayoutManager);

        GridLayoutManager holesLayoutManager = new GridLayoutManager(this, 4);
        rvHoles.setLayoutManager(holesLayoutManager);

        rvLines.setHasFixedSize(true);
        rvHoles.setHasFixedSize(true);

        updateNotifyDataSet_LinesHoles();
    }

    private void updateNotifyDataSet_LinesHoles() {

        MainBusiness_DataLoad mainBusiness_dataLoad = new MainBusiness_DataLoad();
        mainBusiness_dataLoad.setOnDataLoadedLisener(new MainBusiness_DataLoad.OnDataLoadedLisener() {

            @Override
            public void load_Lines_Holes_Dishes(List<Line> linesList, List<Hole> holesList, ArrayMap<String,Plate> plateList) {

                lines.clear();
                holes.clear();
                plates.clear();
                lines.addAll(linesList);
                holes.addAll(holesList);
                plates.putAll(plateList);
                System.out.println("aaa linesList:" + linesList.toString());
                System.out.println("aaa holesList:" + holesList.toString());
                System.out.println("aaa plateList:" + plateList.toString());

                if (null != linesAdapter_main) {
                    linesAdapter_main.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    linesAdapter_main = new LinesAdapter_Main(context, lines);
                    linesAdapter_main.setOnItemClickListener(new mItemClickListener_rvLines());
                    rvLines.setAdapter(linesAdapter_main);
                }

                if (null != holesAdapter_main) {
                    holesAdapter_main.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    holesAdapter_main = new HolesAdapter_Main(context, holes, lines, plates);
                    // TODO: 2017/8/7 点击餐盘，进入该餐盘的设置模式
//                    linesAdapter_main.setOnItemClickListener(new mItemClickListener_rvLines());
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
                startActivity(intent);
                break;
            case R.id.ll_downloadMenu:

                break;
            case R.id.ll_uploadPlan:

                break;
            case R.id.ll_orderList:

                break;
            case R.id.ll_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_allLines:
                updateNotifyDataSet_LinesHoles();
                break;
        }
    }


    /**
     * 餐线列表点击事件  的  回调监听器
     */
    class mItemClickListener_rvLines implements LinesAdapter_Main.ItemClickListener {

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
            public void load_Lines_Holes_Dishes(List<Line> linesList, List<Hole> holesList, ArrayMap<String,Plate> plateList) {
                // TODO: 2017/8/7 餐线点击后，下载完成该餐线的餐盘信息，需完成：切换餐盘列表
                System.out.println("aaa 点击了餐线 holes:"+holesList.toString());
                holes.clear();
                holes.addAll(holesList);
                holesAdapter_main.notifyDataSetChanged();

            }
        });
        mainBusiness_dataLoad.getList_PlatesByLineName(lineName);
    }

}
