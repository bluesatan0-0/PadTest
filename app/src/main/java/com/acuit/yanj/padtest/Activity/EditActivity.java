package com.acuit.yanj.padtest.Activity;

import android.os.Bundle;
import android.view.View;

import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.R;

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

public class EditActivity extends BaseActivity implements View.OnClickListener {

    private View btnSave;
    private View btnDownload;
    private View btnClear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        btnSave = findViewById(R.id.ll_savePlan);
        btnDownload = findViewById(R.id.ll_downloadMenu);
        btnClear = findViewById(R.id.ll_Clear);

    }

    private void initData() {

    }

    private void initEvent() {

        btnSave.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_savePlan:
                // TODO: 2017/8/3 增加保存排菜信息的代码
                finish();

                break;
            case R.id.ll_downloadMenu:

                break;
            case R.id.ll_Clear:

                break;
        }
    }
}
