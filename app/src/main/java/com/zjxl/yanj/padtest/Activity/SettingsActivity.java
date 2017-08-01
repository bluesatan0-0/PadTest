package com.zjxl.yanj.padtest.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zjxl.yanj.padtest.Adapter.HolesAdapter;
import com.zjxl.yanj.padtest.Adapter.LinesAdapter;
import com.zjxl.yanj.padtest.Base.BaseActivity;
import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Presenter.SettingsPresenter_AddHole;
import com.zjxl.yanj.padtest.Presenter.SettingsPresenter_AddLine;
import com.zjxl.yanj.padtest.Presenter.SettingsPresenter_DataLoad;
import com.zjxl.yanj.padtest.Presenter.SettingsPresenter_DeleteLine;
import com.zjxl.yanj.padtest.R;
import com.zjxl.yanj.padtest.Utils.SharedPreference_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: SettingsActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/25 11:25 <p>
 * 描述：
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    private View btnBack;
    private View btnServer;
    private Button btnAddHole;
    private Button btnAddLine;
    private RecyclerView rvLines;
    private RecyclerView rvHoles;

    private Context context;
    private LayoutInflater layoutInflater;

    private List<Line> lines;
    private List<Hole> holes;
    private LinesAdapter linesAdapter;
    private HolesAdapter holesAdapter;

    private View alertView = null;
    private int flag;
    private static final int FLAG_EDIT_SERVER = 0;
    private static final int FLAG_ADD_LINE = 1;
    private static final int FLAG_ADD_HOLE = 2;
    private static final int FLAG_DELETE_LINE = 3;
    private static final int FLAG_EDIT_LINE = 4;
    private Button btnAllLines;
    private String lineName_ForDelete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
        initData();
        initEvent();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {

        btnBack = findViewById(R.id.ll_back_SettingsMenu);
        btnServer = findViewById(R.id.ll_server_SettingsMenu);

        btnAddHole = (Button) findViewById(R.id.btn_addHole);
        btnAddLine = (Button) findViewById(R.id.btn_addLine);

        btnAllLines = (Button) findViewById(R.id.btn_allLines);

        rvLines = (RecyclerView) findViewById(R.id.rv_Lines);
        rvHoles = (RecyclerView) findViewById(R.id.rv_devices_belong_line);

    }

    private void initData() {

        context = this;
        layoutInflater = this.getLayoutInflater();
        lines = new ArrayList<Line>();
        holes = new ArrayList<Hole>();

        /** RecyclerView初始化布局、动画 */
        rvLines.setHasFixedSize(true);
        rvHoles.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_lines = new LinearLayoutManager(context);
        LinearLayoutManager linearLayoutManager_holes = new LinearLayoutManager(context);
        rvLines.setLayoutManager(linearLayoutManager_lines);
        rvHoles.setLayoutManager(linearLayoutManager_holes);

//        初始化列表(餐线、餐眼)数据

        updateNotifyDataSet_LinesHoles();

    }

    /**
     * 更新数据  两个RecyclerView
     */
    private void updateNotifyDataSet_LinesHoles() {

        SettingsPresenter_DataLoad settingsPresenter_dataLoad = new SettingsPresenter_DataLoad();
        settingsPresenter_dataLoad.setOnDataLoadedLisener(new SettingsPresenter_DataLoad.OnDataLoadedLisener() {
            @Override
            public void loaded_Lines(List<Line> linesList) {

            }

            @Override
            public void loaded_Holes(List<Hole> holesList) {

            }

            @Override
            public void load_Lines_Holes(List<Line> linesList, List<Hole> holesList) {

                lines.clear();
                holes.clear();
                lines.addAll(linesList);
                holes.addAll(holesList);
                System.out.println("aaa linesList:"+linesList.toString());
                System.out.println("aaa holesList:"+holesList.toString());

                if (null != linesAdapter) {
                    linesAdapter.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    linesAdapter = new LinesAdapter(context, lines);
                    linesAdapter.setOnItemClickListener(new mItemClickListener_rvLines());
                    rvLines.setAdapter(linesAdapter);
                }

                if (null != holesAdapter) {
                    holesAdapter.notifyDataSetChanged();
                } else {
//                    若空则认为是初始化，实例化适配器
                    holesAdapter = new HolesAdapter(context, holes, lines);
                    rvHoles.setAdapter(holesAdapter);
                }

            }
        });

        settingsPresenter_dataLoad.getList_LinesAndHoles();
    }

    private void initEvent() {

//        返回主页
        btnBack.setOnClickListener(this);

//        设置服务器
        btnServer.setOnClickListener(this);

//        餐线的增(删改在adapter中)
        btnAddLine.setOnClickListener(this);

//        餐眼的增(删改在adapter中)
        btnAddHole.setOnClickListener(this);

//        显示所有餐线
        btnAllLines.setOnClickListener(this);

    }


    /**
     * 设置模块 主界面  按钮点击事件
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            返回主页
            case R.id.ll_back_SettingsMenu:
                onBackPressed();
                break;
            case R.id.btn_allLines:
                updateNotifyDataSet_LinesHoles();
                break;
//            设置服务器
            case R.id.ll_server_SettingsMenu:
                flag = FLAG_EDIT_SERVER;
                showAlert();
                break;
//            添加餐线
            case R.id.btn_addLine:
                flag = FLAG_ADD_LINE;
                showAlert();
                break;
//            添加餐眼
            case R.id.btn_addHole:
                flag = FLAG_ADD_HOLE;
                showAlert();
                break;
        }
    }



    /**
     * 显示提示框
     */
    private void showAlert() {

        switch (flag) {
//            编辑服务器
            case FLAG_EDIT_SERVER:
                alertView = layoutInflater.inflate(R.layout.alert_settings_edit_server, null);
                ArrayMap<String, String> configs = SharedPreference_Utils.getInstance(this).getConfigs();
//                获取配置数据、展示
                ((EditText) alertView.findViewById(R.id.et_server_ip)).setText(configs.get(SharedPreference_Utils.KEY_REMOTE_SERVER_IP));
                ((EditText) alertView.findViewById(R.id.et_server_department)).setText(configs.get(SharedPreference_Utils.KEY_REMOTE_SERVER_DEPATEMENT_CODE));
                ((EditText) alertView.findViewById(R.id.et_db_ip)).setText(configs.get(SharedPreference_Utils.KEY_DB_IP));
                ((EditText) alertView.findViewById(R.id.et_db_port)).setText(configs.get(SharedPreference_Utils.KEY_DB_PORT));
                ((EditText) alertView.findViewById(R.id.et_db_name)).setText(configs.get(SharedPreference_Utils.KEY_DB_NAME));
                ((EditText) alertView.findViewById(R.id.et_db_user)).setText(configs.get(SharedPreference_Utils.KEY_DB_USER));
                ((EditText) alertView.findViewById(R.id.et_db_pwd)).setText(configs.get(SharedPreference_Utils.KEY_DB_PWD));

                break;
//            添加餐线
            case FLAG_ADD_LINE:
                alertView = layoutInflater.inflate(R.layout.alert_settings_line_add, null);
                break;
//            添加餐眼
            case FLAG_ADD_HOLE:
                alertView = layoutInflater.inflate(R.layout.alert_settings_add_hole, null);
                break;
//            删除餐线
            case FLAG_DELETE_LINE:
                alertView = layoutInflater.inflate(R.layout.alert_ensure, null);
                TextView tvTitle = (TextView) alertView.findViewById(R.id.tv_title_ensure);
                TextView tvMessage = (TextView) alertView.findViewById(R.id.tv_message_ensure);
                tvTitle.setText(R.string.alert_settings_title_line_delete);
                tvMessage.setText(R.string.alert_settings_ensure_line_delete);
                break;
//            编辑餐线
            case FLAG_EDIT_LINE:
                alertView = layoutInflater.inflate(R.layout.alert_settings_line_edit, null);
                break;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ensure, this);

        AlertDialog alertDialog = builder.create();

//        图片控件距离对话框边界为0
        alertDialog.setView(alertView, 0, 0, 0, 0);
        alertDialog.show();

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        alertDialog.getWindow().setAttributes(layoutParams);

    }


    /**
     * 提示窗口alertDialog中  按钮单击事件：
     *
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(final DialogInterface dialog, int which) {
        // TODO: 2017/7/28 使用which，布局文件中添加按钮，取代代码中添加的“确认”、“取消”
        switch (flag) {
            case FLAG_EDIT_SERVER:
                ArrayMap<String, String> configs = new ArrayMap<>();

//                确认的是保存服务器设置，校验数据，储存数据，获取新服务器中数据，通知更新
                // TODO: 2017/7/28  数据校验（连接测试）

                configs.put(SharedPreference_Utils.KEY_DB_IP, ((EditText) alertView.findViewById(R.id.et_db_ip)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_DB_PORT, ((EditText) alertView.findViewById(R.id.et_db_port)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_DB_NAME, ((EditText) alertView.findViewById(R.id.et_db_name)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_DB_USER, ((EditText) alertView.findViewById(R.id.et_db_user)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_DB_PWD, ((EditText) alertView.findViewById(R.id.et_db_pwd)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_REMOTE_SERVER_IP, ((EditText) alertView.findViewById(R.id.et_server_ip)).getText().toString());
                configs.put(SharedPreference_Utils.KEY_REMOTE_SERVER_DEPATEMENT_CODE, ((EditText) alertView.findViewById(R.id.et_server_department)).getText().toString());

                SharedPreference_Utils.getInstance(this).setValues(configs);

                dialog.dismiss();
                break;
            case FLAG_ADD_LINE:
//                确认的是添加餐线，校验数据，存储数据，获取新餐线列表，通知更新
                Line line = new Line();
                line.setName(((EditText) alertView.findViewById(R.id.et_line_name)).getText().toString());

                if (!line.getName().isEmpty()) {
                    addLine_ToDB(line);
                } else {
                    Toast.makeText(this, "餐线名称不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case FLAG_ADD_HOLE:
//                确认的是添加餐眼，校验数据，存储数据，获取新餐眼列表，通知更新
                addHole_CheckAndUpdate();

                break;

//            删除餐线，相应的保温眼也将被删除
            case FLAG_DELETE_LINE:
                if (null == lineName_ForDelete || lineName_ForDelete.isEmpty()) {
                    System.out.println("aaa 没选餐线而执行了餐线删除函数");
                    break;
                }
                deleteLine_ToDB();
                break;

//            编辑餐线
            case FLAG_EDIT_LINE:
                break;
        }
    }

    /**
     * 添加餐眼 数据校验 往数据库存储
     */
    private void addHole_CheckAndUpdate() {
        //                校验
        String holeUuid = ((EditText) alertView.findViewById(R.id.et_hole_uuid)).getText().toString();
        if (holeUuid.isEmpty()) {
            Toast.makeText(this, "餐眼编码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String lineName = ((EditText) alertView.findViewById(R.id.et_line_name)).getText().toString();
        if (lineName.isEmpty()) {
            Toast.makeText(this, "所属餐线不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String holeName = ((EditText) alertView.findViewById(R.id.et_hole_name)).getText().toString();
        if (holeName.isEmpty()) {
            Toast.makeText(this, "餐眼名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String num = ((EditText) alertView.findViewById(R.id.et_line_num)).getText().toString();
        if (num.isEmpty()) {
            Toast.makeText(this, "餐线内序号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int lineID = -1;
        for (Line l : lines) {
            if (lineName.equals(l.getName())) lineID = l.getId();
        }
        if (-1 == lineID) {
            Toast.makeText(this, "餐线名称不存在", Toast.LENGTH_SHORT).show();
            return;
        }

//                存储
        String departmentID = SharedPreference_Utils.getConfigs().get(SharedPreference_Utils.KEY_REMOTE_SERVER_DEPATEMENT_CODE);

        Hole hole = new Hole();
        hole.setLineId(lineID);
        hole.setName(holeName);
        hole.setUuid(holeUuid);
        hole.setNum(new Integer(num));
        hole.setDepId(new Integer(departmentID));

        addHole_ToDB(hole);

        lineID = -1;
    }

    /**
     * 往数据库 存储餐眼
     *
     * @param hole 要存储的餐眼实例
     */
    private void addHole_ToDB(Hole hole) {
        SettingsPresenter_AddHole settingsPresenter_addHole = new SettingsPresenter_AddHole();
        settingsPresenter_addHole.setOnAddHoleLisener(new SettingsPresenter_AddHole.OnAddHoleLisener() {
            @Override
            public void error() {
                // TODO: 2017/7/29 优化：错误提示后，窗口不消失；或者改用布局文件添加按钮并监听,运用dialog.dismiss();
                Toast.makeText(getApplicationContext(), "insert餐眼失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void existUuid() {
                Toast.makeText(getApplicationContext(), "该uuid已存在！", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void existRowNum() {
                Toast.makeText(getApplicationContext(), "所填餐线内序号已存在！", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success() {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//                            添加成功后更新lines列表
                updateNotifyDataSet_LinesHoles();

            }
        });
        settingsPresenter_addHole.addHole(hole);
    }

    /**
     * 删除餐线
     */
    private void deleteLine_ToDB() {
        SettingsPresenter_DeleteLine settingsPresenter_deleteLine = new SettingsPresenter_DeleteLine();
        settingsPresenter_deleteLine.setOnDeleteLineLisener(new SettingsPresenter_DeleteLine.OnDeleteLineLisener() {
            @Override
            public void error() {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success() {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                updateNotifyDataSet_LinesHoles();
                lineName_ForDelete = "";
            }
        });
        settingsPresenter_deleteLine.deleteLine(new Line(lineName_ForDelete));
    }

    /**
     * 往数据库 添加餐线
     *
     * @param line 需要新增的餐线实例
     */
    private void addLine_ToDB(Line line) {
        SettingsPresenter_AddLine settingsPresenter_AddDevice_addLine = new SettingsPresenter_AddLine();
        settingsPresenter_AddDevice_addLine.setOnAddLineLisener(new SettingsPresenter_AddLine.OnAddLineLisener() {
            @Override
            public void error() {
                // TODO: 2017/7/29 优化：错误提示后，窗口不消失；或者改用布局文件添加按钮并监听,运用dialog.dismiss();
                Toast.makeText(getApplicationContext(), "insert 餐线 error！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void exist() {
                // TODO: 2017/7/29 优化：错误提示后，窗口不消失；或者改用布局文件添加按钮并监听,运用dialog.dismiss();
                Toast.makeText(getApplicationContext(), "该餐线名称已存在，请改用其他名称！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success() {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//                            添加成功后更新lines列表
                updateNotifyDataSet_LinesHoles();

            }
        });
        settingsPresenter_AddDevice_addLine.addLine(line);
    }

    //    private void getTime(String text, long currentTime) {
//        date = new Date(currentTime);
//        if (null == simpleDateFormat) {
//            simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
//        }
//        time = simpleDateFormat.format(date);
//        System.out.println(text + time);
//    }

    /**
     * 餐线点击事件  的  回调监听器
     */
    class mItemClickListener_rvLines implements LinesAdapter.ItemClickListener {

        @Override
        public void onBtnNameClick(String lineName) {

            whenBtnLineNameClick(lineName);
        }


        @Override
        public void onDeleteClick(String lineName) {
            // TODO: 2017/7/31   编写对应对话框
            flag = FLAG_DELETE_LINE;
            lineName_ForDelete = lineName;
            showAlert();
        }

        @Override
        public void onEditClick(String lineName) {
            // TODO: 2017/7/31   编写对应对话框
            flag = FLAG_EDIT_LINE;
            showAlert();
        }

        /**
         * 根据点击的line  更新rvHoles中的数据（换成被点击餐线的餐眼）
         * 当rvLines的item中btnName被点击时，执行
         *
         * @param lineName 餐线名称
         */
        private void whenBtnLineNameClick(String lineName) {
            SettingsPresenter_DataLoad settingsPresenter_dataLoad_HolesByLinesName = new SettingsPresenter_DataLoad();
            settingsPresenter_dataLoad_HolesByLinesName.setOnDataLoadedLisener(new SettingsPresenter_DataLoad.OnDataLoadedLisener() {
                @Override
                public void loaded_Lines(List<Line> linesList) {

                }

                @Override
                public void loaded_Holes(List<Hole> holesList) {
                    holes.removeAll(holes);
                    holes.clear();
                    holes.addAll(holesList);
                    System.out.println("aaa whenBtnLineNameClick loaded_Holes_list:" + holesList.toString());
                    holesAdapter.notifyDataSetChanged();
                }

                @Override
                public void load_Lines_Holes(List<Line> linesList, List<Hole> holesList) {

                }
            });
            settingsPresenter_dataLoad_HolesByLinesName.getList_HolesByLinesName(lineName);
        }

    }

}
