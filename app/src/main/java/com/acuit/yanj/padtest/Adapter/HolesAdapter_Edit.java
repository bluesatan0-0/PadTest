package com.acuit.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuit.yanj.padtest.Base.BaseApplication;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.MyImageLoader;

import java.util.List;

/**
 * 类名: HolesAdapter_Edit <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 主页界面——设备列表，适配器
 * <p>
 * 更新人: 严江<p>
 * 更新时间: 2017-08-05 14:33:30<p>
 * 更新描述: 修改为首页餐眼item <p>
 */

public class HolesAdapter_Edit extends RecyclerView.Adapter {

    private final MyImageLoader imageLoader;
    private BaseApplication context;
    private List<Hole> holesList;
    private List<Line> linesList;
    private ArrayMap<String, Plate> plateList;
    private Line line;
    private ViewHolder_HolesAdapter preSelected_ViewHolder = null;
    private OnItemClickListener itemClickListener;

    public HolesAdapter_Edit(Context context, List<Hole> holes, List<Line> linesList, ArrayMap<String, Plate> plateList) {
        this.context = (BaseApplication) context.getApplicationContext();
        this.holesList = holes;
        this.linesList = linesList;
        this.plateList = plateList;
        imageLoader = this.context.getImageLoader();
    }

    @Override
    public ViewHolder_HolesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_hole_main, parent, false);
        if (null == itemClickListener) {
            System.out.println("aaa HolesAdapter_Edit.itemClickListener is null");
        }
        return new ViewHolder_HolesAdapter(view, itemClickListener);
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
        } else {
            for (Line line : linesList) {
                if (lineId == line.getId()) {
                    this.line = line;
                    code.append(line.getCode());
                }
            }
        }
//        获取餐眼num
        if (lineNum < 10) {
            code.append("0");
        }
        code.append("" + lineNum);

//        获取lineCode👆


        ViewHolder_HolesAdapter holder_HolesAdapter = (ViewHolder_HolesAdapter) holder;

        holder_HolesAdapter.tvCode.setText(code.toString());
        holder_HolesAdapter.itemPosition = position;

        Plate plate = plateList.get(hole.getUuid());
        int statuResID = R.mipmap.item_hole_empty;

//        未排菜的餐眼
        if (null == plate) {

            holder_HolesAdapter.tvCode.setTextColor(0xffADADAD);
            holder_HolesAdapter.tvCode.setBackgroundColor(0xffF2F2F2);

            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
        } else {

//        存在菜品👇 在线离线
            if (1 == hole.getStatu()) {
                statuResID = R.mipmap.item_hole_online;
            } else {
                statuResID = R.mipmap.item_hole_offline;
            }

            holder_HolesAdapter.tvCode.setTextColor(0xffCBA99A);
            holder_HolesAdapter.tvCode.setBackgroundColor(0xffFFF6F2);
//        获取holeStatu👆
            holder_HolesAdapter.ivStatu.setImageResource(statuResID);
            holder_HolesAdapter.tvDishPrice.setText(plate.getPrice() + "");
            holder_HolesAdapter.tvDishName.setText(plate.getDish_name());
            holder_HolesAdapter.tvWeight.setText(plate.getLeft_amount() + "");


//        获取菜品图片
//            'http://192.168.2.241/skin/images/no_cai_pic.jpg
            String menu_url = plate.getMenu_url();
            if (null != menu_url && !menu_url.isEmpty()) {
                imageLoader.setBitmapToImageView(menu_url, holder_HolesAdapter.ivDish);
            }

        }

        if (null == HolesAdapter_Edit.this.preSelected_ViewHolder) {
            HolesAdapter_Edit.this.preSelected_ViewHolder = (ViewHolder_HolesAdapter) holder;
            HolesAdapter_Edit.this.preSelected_ViewHolder.itemView.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return holesList.size();
    }

    /**
     * item点击回调 接口
     */
    public interface OnItemClickListener {
        void onItemViewClick(int position);
    }

    /**
     * 外部设置 回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    class ViewHolder_HolesAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener onItemClickListener;
        public int itemPosition;
        public ImageView ivStatu;
        public ImageView ivDish;
        public TextView tvCode;
        public TextView tvDishName;
        public TextView tvDishPrice;
        public TextView tvWeight;

        public View itemView;

        // TODO: 2017/8/7 item点击事件回调（餐盘item点击进入该餐盘的编辑模式）

        public ViewHolder_HolesAdapter(View itemView, OnItemClickListener listener) {
            super(itemView);

            this.itemView = itemView;
            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            tvDishPrice = (TextView) itemView.findViewById(R.id.tv_dish_price);
            tvWeight = (TextView) itemView.findViewById(R.id.tv_weight);

            ivStatu = (ImageView) itemView.findViewById(R.id.iv_statu);
            ivDish = (ImageView) itemView.findViewById(R.id.iv_dish_icon);

            itemView.setOnClickListener(this);
            onItemClickListener = listener;

        }

        @Override
        public void onClick(View v) {

            HolesAdapter_Edit.this.preSelected_ViewHolder.itemView.setSelected(false);
            v.setSelected(true);

            preSelected_ViewHolder = this;
            onItemClickListener.onItemViewClick(itemPosition);
        }
    }

}
