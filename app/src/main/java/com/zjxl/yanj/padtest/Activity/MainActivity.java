package com.zjxl.yanj.padtest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.R;

/**
 * 类名: MainActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 11:32 <p>
 * 描述: MainActivity     首页
 * <p>
 * 更新人: yanj<p>
 * 更新时间: 2017-08-03 14:20:29 <p>
 * 更新描述: 实现功能<p>
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View btnPlanDish;
    private View btnDownloadMenu;
    private View btnUploadPlan;
    private View btnOrderList;
    private View btnSettings;
    private RecyclerView rvHoles;
    private RecyclerView rvLines;

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

        rvLines = (RecyclerView) findViewById(R.id.rv_lines);
        rvHoles = (RecyclerView) findViewById(R.id.rv_holes);
    }

    private void initData() {

        LinearLayoutManager linesLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvLines.setLayoutManager(linesLayoutManager);

        GridLayoutManager holesLayoutManager = new GridLayoutManager(this, 4);
        rvHoles.setLayoutManager(holesLayoutManager);
    }

    private void initEvent() {

        btnPlanDish.setOnClickListener(this);
        btnDownloadMenu.setOnClickListener(this);
        btnUploadPlan.setOnClickListener(this);
        btnOrderList.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
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
        }
    }
}
