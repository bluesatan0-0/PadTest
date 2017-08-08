package com.acuit.yanj.padtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuit.yanj.padtest.Base.BaseApplication;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.MyImageLoader;

import java.util.List;

/**
 * 类名: LinesAdapter <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/7/26 16:16 <p>
 * 描述: 设置界面——餐线列表，适配器
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class DishesAdapter extends RecyclerView.Adapter {


    private Context context;
    private OnItemClickListener itemClickListener;
    private List<Dish> dishes;
    private MyImageLoader imageLoader;


    public DishesAdapter(Context context, List<Dish> dishesList) {
        this.context = context;
        this.dishes = dishesList;
        this.imageLoader = BaseApplication.getImageLoader();
    }

    @Override
    public ViewHolder_LinesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_dish_edit, parent, false);
        if (null == itemClickListener) {
            System.out.println("aaa itemClickListener is null");
        }
        return new ViewHolder_LinesAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Dish dish = dishes.get(position);
        ViewHolder_LinesAdapter viewHolder = (ViewHolder_LinesAdapter) holder;
        viewHolder.itemPosition = position;
        viewHolder.tvDishName.setText(dish.getName());
        imageLoader.setBitmapToImageView(dish.getPic(), viewHolder.ivDishPic);

    }


    @Override
    public int getItemCount() {
        return dishes.size();
    }


    /**
     * item点击回调 接口
     */
    public interface OnItemClickListener {

        void onItemViewClick(Dish dish);
    }

    /**
     * 外部设置 回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    class ViewHolder_LinesAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivDishPic;
        public TextView tvDishName;
        public int itemPosition = -1;
        private OnItemClickListener mItemClickListener;

        public ViewHolder_LinesAdapter(View itemView, OnItemClickListener listener) {
            super(itemView);

            ivDishPic = (ImageView) itemView.findViewById(R.id.iv_dish_pic);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);

            mItemClickListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (-1 != itemPosition) {
                mItemClickListener.onItemViewClick(dishes.get(itemPosition));
            }
        }
    }


}
