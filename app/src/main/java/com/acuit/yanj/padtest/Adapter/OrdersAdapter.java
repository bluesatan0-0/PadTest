package com.acuit.yanj.padtest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acuit.yanj.padtest.Activity.OrderListActivity;
import com.acuit.yanj.padtest.Bean.Trade;
import com.acuit.yanj.padtest.R;
import com.acuit.yanj.padtest.Utils.Tools;

import java.util.List;

/**
 * 类名: OrdersAdapter <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/9/19 17:03 <p>
 * 描述:
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class OrdersAdapter extends RecyclerView.Adapter {

    OrderListActivity mActivity;
    List<Trade> dataList;

    public OrdersAdapter(OrderListActivity mActivity, List<Trade> dataList) {

        System.out.println("aaa OrdersAdapter");
        this.mActivity = mActivity;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        System.out.println("aaa onCreateViewHolder");
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_orders, null, false);
        return new ViewHolder_Orders(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        System.out.println("aaa onBindViewHolder");
        ViewHolder_Orders viewHolder = (ViewHolder_Orders) holder;
        Trade trade = dataList.get(position);
        viewHolder.getTvAmount().setText(trade.getAmount());
        viewHolder.getTvDate().setText(Tools.getSimpleFormatedTime(trade.getTradeDate()));
        viewHolder.getTvDishName().setText(trade.getDishId());
        viewHolder.getTvMoney().setText(trade.getMoney() + "");
        viewHolder.getTvOrderID().setText(trade.getId());
        viewHolder.getTvStudentID().setText(trade.getUserId());
        viewHolder.getTvStudentName().setText(trade.getUserId());
        if (trade.getIsUpload().equals("1")) {
            viewHolder.getTvStatu().setText("上传成功");
        } else {
            viewHolder.getTvStatu().setText("上传失败");
            viewHolder.getTvStatu().setTextColor(0xFA3F3F);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder_Orders extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvMoney;
        private TextView tvStatu;
        private TextView tvAmount;
        private TextView tvOrderID;
        private TextView tvDishName;
        private TextView tvStudentID;
        private TextView tvStudentName;

        public ViewHolder_Orders(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_sum);
            tvStatu = (TextView) itemView.findViewById(R.id.tv_statu);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvOrderID = (TextView) itemView.findViewById(R.id.tv_orderID);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dishName);
            tvStudentID = (TextView) itemView.findViewById(R.id.tv_studentNumber);
            tvStudentName = (TextView) itemView.findViewById(R.id.tv_studentName);
        }


        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getTvMoney() {
            return tvMoney;
        }

        public TextView getTvStatu() {
            return tvStatu;
        }

        public TextView getTvAmount() {
            return tvAmount;
        }

        public TextView getTvOrderID() {
            return tvOrderID;
        }

        public TextView getTvDishName() {
            return tvDishName;
        }

        public TextView getTvStudentID() {
            return tvStudentID;
        }

        public TextView getTvStudentName() {
            return tvStudentName;
        }
    }

}
