package com.acuit.yanj.padtest.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.acuit.yanj.padtest.Adapter.OrdersAdapter;
import com.acuit.yanj.padtest.Base.BaseActivity;
import com.acuit.yanj.padtest.Bean.Trade;
import com.acuit.yanj.padtest.Model.MainBusiness.Orders_DataLoad;
import com.acuit.yanj.padtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: OrderListActivity <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/9/18 15:56 <p>
 * 描述: 交易记录页面
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private View btnBack;
    private RecyclerView rvOrders;
    private List<Trade> trades;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        btnBack = findViewById(R.id.ll_back_Orders);
        rvOrders = (RecyclerView) findViewById(R.id.rv_orders);

    }


    private void initEvent() {

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back_Orders:
                finish();
                break;
        }
    }

    private void initData() {

        Orders_DataLoad orders_dataLoad = new Orders_DataLoad();
        orders_dataLoad.setDataLoadListener(new Orders_DataLoad.DataLoadListener() {
            @Override
            public void error() {
                Toast.makeText(OrderListActivity.this, "下载失败，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(List<Trade> trades) {

                if (null == OrderListActivity.this.trades) {
                    OrderListActivity.this.trades = new ArrayList<Trade>();
                }
                OrderListActivity.this.trades.clear();
                OrderListActivity.this.trades = trades;

                if (null == ordersAdapter) {

                    rvOrders.setLayoutManager(new LinearLayoutManager(OrderListActivity.this, LinearLayoutManager.VERTICAL, false));

                    ordersAdapter = new OrdersAdapter(OrderListActivity.this, OrderListActivity.this.trades);
                    rvOrders.setAdapter(ordersAdapter);

                } else {
                    ordersAdapter.notifyDataSetChanged();
                }

            }
        });
        orders_dataLoad.getOrders();

    }
}
