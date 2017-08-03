package com.zjxl.yanj.padtest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.R;

/**
 * 类名: MainActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 11:32 <p>
 * 描述: MainActivity
 * 首页
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View btnPlanDish;
    private View btnDownloadMenu;
    private View btnUploadPlan;
    private View btnOrderList;
    private View btnSettings;

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


    }

    private void initData() {


    }

    private void initEvent() {

        btnSettings.setOnClickListener(this);
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
