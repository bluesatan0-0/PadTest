package com.acuit.yanj.padtest.Model.MainBusiness;

import android.os.Handler;
import android.os.Message;

import com.acuit.yanj.padtest.Bean.Trade;
import com.acuit.yanj.padtest.Model.DAO.OrderDAO;
import com.acuit.yanj.padtest.Utils.ThreadPool_Util;

import java.util.List;

/**
 * 类名: Orders_DataLoad <p>
 * 创建人: YanJ <p>
 * 创建时间: 2017/9/19 15:52 <p>
 * 描述:
 * <p>
 * 更新人: <p>
 * 更新时间: <p>
 * 更新描述: <p>
 */

public class Orders_DataLoad {

    //    ------------------------------------获取交易订单(Orders)👇-------------------------------------------

    private DataLoadListener dataLoadListener;


    public interface DataLoadListener {
        //        报错
        void error();
        //        成功
        void success(List<Trade> trades);
    }

    public void setDataLoadListener(DataLoadListener dataLoadListener) {
        if (null != dataLoadListener) {
            this.dataLoadListener = dataLoadListener;
        } else {
            System.out.println("aaa onAddLineLisener is null");
        }
    }


    public void getOrders() {

        ThreadPool_Util.doTask(new Runnable() {
            @Override
            public void run() {

                OrderDAO orderDAO = new OrderDAO();
                Message msg = Message.obtain();
                List<Trade> orders = orderDAO.getOrders();
                if (null != orders && orders.size() > 0) {
                    msg.what = OrderDAO.FLAG_SUCCESS;
                    msg.obj = orders;
                } else {
                    msg.what = orderDAO.FLAG_ERROR;
                }
                handler_Orders.sendMessage(msg);
            }
        });
    }

    private Handler handler_Orders = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OrderDAO.FLAG_ERROR:
                    dataLoadListener.error();
                    break;
                case OrderDAO.FLAG_SUCCESS:
                    dataLoadListener.success((List<Trade>)msg.obj);
                    break;
            }
        }
    };


//    ------------------------------------获取交易订单(Orders)👆-------------------------------------------
}
