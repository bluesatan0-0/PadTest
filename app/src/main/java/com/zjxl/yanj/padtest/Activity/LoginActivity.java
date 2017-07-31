package com.zjxl.yanj.padtest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.R;
import com.zjxl.yanj.padtest.Utils.SharedPreference_Utils;

/**
 * 类名: LoginActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/13 13:38 <p>
 * 描述: 设置模块账户登录界面
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etAccount;
    private EditText etPwd;
    private String strAccount;
    private String strPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
        initEvent();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);

    }

    private void initData() {

        ArrayMap<String, String> configs = SharedPreference_Utils.getInstance(this).getConfigs();

        strAccount = configs.get(SharedPreference_Utils.KEY_ADMIN_NAME);
        strPwd = configs.get(SharedPreference_Utils.KEY_ADMIN_NAME);
    }

    private void initEvent() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_login) {

            String str = checkLogin();
            if ("".equals(str)) {
//                进入设置模块
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, checkLogin(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String checkLogin() {

        if (strAccount.isEmpty() || strPwd.isEmpty()) {
            return "error：默认账户异常";
        }
        String account = etAccount.getText().toString();
        String pwd = etPwd.getText().toString();
        if (account.isEmpty()) {
            return "请输入账户";
        }
        if (pwd.isEmpty()) {
            return "请输入密码";
        }
        if (!account.equals(strAccount)) {
            return "账户不正确";
        }
        if (!pwd.equals(strPwd)) {
            return "密码不正确";
        }
        return "";
    }
}
