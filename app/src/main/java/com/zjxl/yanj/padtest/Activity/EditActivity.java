package com.zjxl.yanj.padtest.Activity;

import android.os.Bundle;

import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.R;

/**
 * 类名: EditActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017-08-03 13:34:27 <p>
 * 描述: EditActivity     排菜界面
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class EditActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {

    }

    private void initView() {



    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

}
