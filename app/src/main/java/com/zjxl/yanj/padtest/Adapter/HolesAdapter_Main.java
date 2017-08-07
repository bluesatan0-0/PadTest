package com.zjxl.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjxl.yanj.padtest.Bean.Hole;
import com.zjxl.yanj.padtest.Bean.Line;
import com.zjxl.yanj.padtest.Bean.Plate;
import com.zjxl.yanj.padtest.R;

import java.util.List;

/**
 * 类名: HolesAdapter_Main <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 主页界面——设备列表，适配器
 * <p>
 * <p>
 * 更新人: 严江<p>
 * 更新时间: 2017-08-05 14:33:30<p>
 * 更新描述: 修改为首页餐眼item <p>
 */

public class HolesAdapter_Main extends RecyclerView.Adapter {

    private Context context;
    private List<Hole> holesList;
    private List<Line> linesList;
    private List<Plate> plateList;
    private Line line;

    public HolesAdapter_Main(Context context, List<Hole> holes, List<Line> linesList, List<Plate> plateList) {
        this.context = context;
        this.holesList = holes;
        this.linesList = linesList;
        this.plateList = plateList;
//        headerView对应数据
    }

    @Override
    public ViewHolder_HolesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_hole_main, parent, false);
        return new ViewHolder_HolesAdapter(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Hole hole = holesList.get(position);
        int lineId = hole.getLineId();
        StringBuilder code = new StringBuilder();

//        获取餐线code
        if (null != line && lineId == line.getId()) {
            code.append(line.getCode());
        } else {
            for (Line line : linesList) {
                if (lineId == line.getId()) {
                    this.line = line;
                    code.append(line.getCode());
                }
            }
        }
//        获取餐眼num
        if (hole.getNum() < 10) {
            code.append("0");
        }
        code.append("" + hole.getNum());


//        获取lineCode👆


//        int statuResID = R.mipmap.item_hole_empty;
//        Dish dish = dishesList.get(position);
//        ViewHolder_HolesAdapter holder_HolesAdapter = (ViewHolder_HolesAdapter) holder;
//        if (null == dish) {
//            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
//            holder_HolesAdapter.tvHoleCode.setText(code.toString());
//            holder_HolesAdapter.itemPosition = position;
//        } else {
//
//
////        存在菜品👇 在线离线
//            switch (hole.getStatu()) {
//
//                case 1:
//                    statuResID = R.mipmap.item_hole_online;
//                    break;
//                default:
//                    statuResID = R.mipmap.item_hole_offline;
//                    break;
//            }
////        获取holeStatu👆
//
//            holder_HolesAdapter.tvDishName.setText(code);
//            holder_HolesAdapter.tvDishPrice.setText(dish.getSell_100gram_price());
//            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
////        获取菜品图片
////            holder_HolesAdapter.ivDish.setImageBitmap();
//
////        重量初始化不设置（默认显示0）
////        holder_HolesAdapter.getTvWeight().setText(holeStatu);
//
//        }
    }

    @Override
    public int getItemCount() {
        return holesList.size();
    }



    class ViewHolder_HolesAdapter extends RecyclerView.ViewHolder {

        public int itemPosition;
        public ImageView ivStatu;
        public ImageView ivDish;
        public TextView tvHoleCode;
        public TextView tvDishName;
        public TextView tvDishPrice;
        public TextView tvWeight;

        public ViewHolder_HolesAdapter(View itemView) {
            super(itemView);

            tvHoleCode = (TextView) itemView.findViewById(R.id.tv_hole_code);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            tvDishPrice = (TextView) itemView.findViewById(R.id.tv_dish_price);
            tvWeight = (TextView) itemView.findViewById(R.id.tv_weight);

            ivStatu = (ImageView) itemView.findViewById(R.id.iv_statu);
            ivDish = (ImageView) itemView.findViewById(R.id.iv_dish_icon);

        }

    }

}
