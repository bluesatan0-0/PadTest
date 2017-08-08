package com.zjxl.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
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
    private ArrayMap<String, Plate> plateList;
    private Line line;

    public HolesAdapter_Main(Context context, List<Hole> holes, List<Line> linesList, ArrayMap<String, Plate> plateList) {
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
        int lineNum = hole.getNum();
        StringBuilder code = new StringBuilder();

//        获取餐线code
        if (null != line && lineId == line.getId()) {
            code.append(line.getCode());
            System.out.println("aaa 查找餐眼对应餐线 优化成功~");
        } else {
            for (Line line : linesList) {
                if (lineId == line.getId()) {
                    this.line = line;
                    code.append(line.getCode());
                    System.out.println("aaa 查找餐眼对应餐线 失败------");
                }
            }
        }
//        获取餐眼num
        if (lineNum < 10) {
            code.append("0");
        }
        code.append("" + lineNum);


//        获取lineCode👆


        int statuResID = R.mipmap.item_hole_empty;

        Plate plate = plateList.get(hole.getUuid());

        ViewHolder_HolesAdapter holder_HolesAdapter = (ViewHolder_HolesAdapter) holder;

        holder_HolesAdapter.tvCode.setText(code.toString());
        holder_HolesAdapter.itemPosition = position;

//        未排菜的餐眼
        if (null==plate) {
            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
        } else {

//        存在菜品👇 在线离线
            if (1 == hole.getStatu()) {
                statuResID = R.mipmap.item_hole_online;
            } else {
                statuResID = R.mipmap.item_hole_offline;
            }

            holder_HolesAdapter.tvCode.setBackgroundColor(0xffFFF6F2);
            holder_HolesAdapter.tvCode.setTextColor(0xffCBA99A);
//        获取holeStatu👆
            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
            holder_HolesAdapter.tvDishPrice.setText(plate.getPrice() + "");
            holder_HolesAdapter.tvDishName.setText(plate.getDish_name());
            holder_HolesAdapter.tvWeight.setText(plate.getLeft_amount() + "");


//        获取菜品图片
            // TODO: 2017/8/7 下载菜品图片
            // TODO: 2017/8/7 三级缓存策略
//            holder_HolesAdapter.ivDish.setImageBitmap();

        }
    }

    @Override
    public int getItemCount() {
        return holesList.size();
    }


    class ViewHolder_HolesAdapter extends RecyclerView.ViewHolder {

        public int itemPosition;
        public ImageView ivStatu;
        public ImageView ivDish;
        public TextView tvCode;
        public TextView tvDishName;
        public TextView tvDishPrice;
        public TextView tvWeight;

        // TODO: 2017/8/7 item点击事件回调（餐盘item点击进入该餐盘的编辑模式）

        public ViewHolder_HolesAdapter(View itemView) {
            super(itemView);

            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            tvDishPrice = (TextView) itemView.findViewById(R.id.tv_dish_price);
            tvWeight = (TextView) itemView.findViewById(R.id.tv_weight);

            ivStatu = (ImageView) itemView.findViewById(R.id.iv_statu);
            ivDish = (ImageView) itemView.findViewById(R.id.iv_dish_icon);

        }

    }

}
