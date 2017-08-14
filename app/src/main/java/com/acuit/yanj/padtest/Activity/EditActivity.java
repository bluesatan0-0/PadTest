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

import com.acuit.yanj.padtest.Adapter.DishesAdapter;
import com.acuit.yanj.padtest.Adapter.HolesAdapter_Edit;
import com.acuit.yanj.padtest.Adapter.LinesAdapter;
import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.Base.BaseArrayList;
import com.acuit.yanj.padtest.Base.BaseArrayMap;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.EditBusiness.EditBusiness_DataLoad;
import com.acuit.yanj.padtest.Model.MainBusiness.MainBusiness_DataLoad;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.GetPlate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    private RecyclerView rvHoles;
    private RecyclerView rvLines;
    private RecyclerView rvMenu;

    private BaseArrayList<Line> lines;
    private BaseArrayList<Hole> holes;
    private BaseArrayList<Dish> dishes;
    private BaseArrayMap<String, Plate> plates;

    private BaseArrayList<Hole> tempHoles;
    private BaseArrayMap<String, Plate> tempPlates;

    private LinesAdapter linesAdapter;
    private HolesAdapter_Edit holesAdapter;
    private DishesAdapter dishesAdapter;

    private Context context;
    private Button btnAllLines;
    private int selectedHolePosition;
    private BaseArrayList<String> invalidateHolesUuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
//        updateNotifyDataSet_LinesHoles();
//        避免排菜信息未上传丢失

        comparisonPlatesDishes();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ReturnResult();
    }

    private void initView() {

        btnClear = findViewById(R.id.ll_Clear);
        btnSave = findViewById(R.id.ll_savePlan);
        btnDownload = findViewById(R.id.ll_downloadMenu);
        btnAllLines = (Button) findViewById(R.id.btn_allLines);

        rvMenu = (RecyclerView) findViewById(R.id.rv_menu);
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

        tempHoles = new BaseArrayList<Hole>();
        tempPlates = new BaseArrayMap<String, Plate>();

        // TODO: 2017/8/10 使用页面传值，加快显示速度，需重写adapter初始化的逻辑（只查询菜单集合）
        Intent data = getIntent();
        lines.addAll((Collection<? extends Line>) data.getSerializableExtra("Lines"));
        holes.addAll((Collection<? extends Hole>) data.getSerializableExtra("Holes"));
        dishes.addAll((Collection<? extends Dish>) data.getSerializableExtra("Dishes"));
        plates.putAll((Map<? extends String, ? extends Plate>) data.getSerializableExtra("Plates"));
//        invalidateHolesUuid.addAll((Collection<? extends String>) data.getSerializableExtra("invalidateHolesUuid"));
        tempHoles.addAll(holes);
        tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);

        comparisonPlatesDishes();

        System.out.println("aaa lines:" + lines.toString());
        System.out.println("aaa holes:" + holes.toString());
        System.out.println("aaa plates:" + plates.toString());
        System.out.println("aaa dishes:" + dishes.toString());


        LinearLayoutManager linesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLines.setLayoutManager(linesLayoutManager);

        LinearLayoutManager dishesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMenu.setLayoutManager(dishesLayoutManager);

        GridLayoutManager holesLayoutManager = new GridLayoutManager(this, 4);
        rvHoles.setLayoutManager(holesLayoutManager);

        rvMenu.setHasFixedSize(true);
        rvLines.setHasFixedSize(true);
        rvHoles.setHasFixedSize(true);

        initAdapters();

    }

    private void updateNotifyDataSet_LinesHoles() {

        // TODO: 2017/8/9 优化显示速度，使用intent页面传值，避免重复获取Holes、Lines、Plates三个集合（视具体使用场景，局域网速度快可能没必要）
        EditBusiness_DataLoad editBusiness_dataLoad = new EditBusiness_DataLoad();
        editBusiness_dataLoad.setOnDataLoadedLisener(new EditBusiness_DataLoad.OnDataLoadedLisener() {

            @Override
            public void load_Lines_Holes_Plates_Dishes(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishesList) {

                lines.clear();
                holes.clear();
                plates.clear();
                dishes.clear();
                lines.addAll(linesList);
                holes.addAll(holesList);
                dishes.addAll(dishesList);
                plates.putAll(plateList);

                initAdapters();

            }
        });

        editBusiness_dataLoad.getList_Lines_Holes_Plates();
    }

    /**
     * 刷新适配器（界面上三个列表）；若不存在，则初始化
     */
    private void initAdapters() {


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
            holesAdapter = new HolesAdapter_Edit(context, tempHoles, lines, tempPlates, invalidateHolesUuid);
            holesAdapter.setOnItemClickListener(new mItemClickListener_rvHoles());
            rvHoles.setAdapter(holesAdapter);
        }


        if (null != dishesAdapter) {
            dishesAdapter.notifyDataSetChanged();
        } else {
//                    若空则认为是初始化，实例化适配器
            dishesAdapter = new DishesAdapter(context, dishes);
            dishesAdapter.setOnItemClickListener(new mItemClickListener_rvDishes());
            rvMenu.setAdapter(dishesAdapter);
        }


    }

    private void initEvent() {

        btnSave.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        btnAllLines.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_Clear:
                plates.clear();

                holesAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_savePlan:
                ReturnResult();
                break;
            case R.id.ll_downloadMenu:
                downloadMenuList();
                break;

            case R.id.btn_allLines:
//                updateNotifyDataSet_LinesHoles();
                pickHolesByLineClick("全  部");
                break;
        }
    }

    private void ReturnResult() {
        Intent intent;
        intent = new Intent();
        intent.putExtra("Dishes", dishes);
        intent.putExtra("Plates", plates);
        intent.putExtra("invalidateHolesUuid", invalidateHolesUuid);
        setResult(2, intent);
        finish();
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
                dishesAdapter.notifyDataSetChanged();

                comparisonPlatesDishes();

            }
        });

        editBusiness_dataLoad.getList_Dishes();
    }


    /**
     * 比对排菜与菜单，无效化
     */
    private void comparisonPlatesDishes() {

        invalidateHolesUuid.clear();
        boolean isExist = false;
        for (Plate plate : tempPlates.values()) {
            String dish_code = plate.getDish_code();
            isExist = false;
            for (Dish dish : dishes) {
                if ((dish.getStock_id() + "").equals(dish_code)) {
                    isExist = true;
                }
            }
            if (isExist == false) {
                invalidateHolesUuid.add(plate.getDevice_code());
            }
        }

        for (String s : invalidateHolesUuid) {
            System.out.println("aaa editActivity invalidateUuid:" + s);
        }

        if (null != holesAdapter) {
            holesAdapter.notifyDataSetChanged();
        }
    }
//-------------------------------------下载今日菜单?👆-----------------------------------------------------


    /**
     * 待选菜单 列表item 点击事件  的 回调监听器
     * 将菜品设置给选中的餐眼
     */
    class mItemClickListener_rvDishes implements DishesAdapter.OnItemClickListener {

        @Override
        public void onItemViewClick(Dish dish) {
            Hole hole = tempHoles.get(selectedHolePosition);
            Plate plate = GetPlate.GetPlate_FromHoleAndDish(hole, dish);

            int indexOfKey = tempPlates.indexOfKey(hole.getUuid());

//            System.out.println("aaa plates:" + plates.toString());
//            System.out.println("aaa hole.getUuid():" + hole.getUuid());
//            System.out.println("aaa selectedPosition:" + selectedHolePosition);
//            System.out.println("aaa indexOfKey:" + indexOfKey);

            if (0 > indexOfKey) {
                plates.put(hole.getUuid(), plate);
                tempPlates.put(hole.getUuid(), plate);
            } else {
                plates.setValueAt(indexOfKey, plate);
                tempPlates.setValueAt(indexOfKey, plate);
            }

            invalidateHolesUuid.remove(hole.getUuid());

            for (String s : invalidateHolesUuid) {
                System.out.println("aaa editActivity remove invalidateUuid:" + s);
            }

            holesAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 餐眼 列表item 点击事件  的 回调监听器
     * 设置选中状态
     */
    class mItemClickListener_rvHoles implements HolesAdapter_Edit.OnItemClickListener {

        @Override
        public void onItemViewClick(int position) {
            selectedHolePosition = position;
        }
    }


    /**
     * 餐线列表点击事件  的  回调监听器
     */
    class mItemClickListener_rvLines implements LinesAdapter.ItemClickListener {

        @Override
        public void onBtnNameClick(String lineName) {

//            whenBtnLineNameClick(lineName);
            pickHolesByLineClick(lineName);
        }

    }

    /**
     * 点击餐线切换相应的餐眼，弃用whenBtnLineNameClick()
     * 避免未上传的排菜信息丢失
     *
     * @param lineName 点击的餐线名称
     */
    private void pickHolesByLineClick(String lineName) {
        tempHoles.clear();
        tempPlates.clear();

        if (lineName.equals("全  部")) {
            tempHoles.addAll(holes);
            tempPlates.putAll((Map<? extends String, ? extends Plate>) plates);
        } else {

            int lineId = -1;

            for (Line line : lines) {
                if (lineName.equals(line.getName())) {
                    lineId = line.getId();
                }
            }

            for (Hole hole : holes) {
                if (hole.getLineId() == lineId) {
                    tempHoles.add(hole);
                }
            }


            for (String holeUuid : plates.keySet()) {
                for (Hole hole : tempHoles) {
                    if (hole.getUuid().equals(holeUuid)) {
                        tempPlates.put(holeUuid, plates.get(holeUuid));
                    }
                }
            }
        }
        holesAdapter.notifyDataSetChanged();
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
            public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishesList) {
                System.out.println("aaa 点击了餐线 holes:" + holesList.toString());
                holes.clear();
                holes.addAll(holesList);
                holesAdapter.notifyDataSetChanged();

            }
        });
        mainBusiness_dataLoad.getList_PlatesByLineName(lineName);
    }

}

