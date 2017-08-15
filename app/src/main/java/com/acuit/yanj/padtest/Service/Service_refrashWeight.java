package com.acuit.yanj.padtest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.ArrayMap;

import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.DAO.PlateDAO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Service_refrashWeight extends Service {

    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler;
    private ArrayMap<String, Plate> plates;
    private ServiceCallBack callBack;

    public Service_refrashWeight() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        scheduledExecutorService = Executors.newScheduledThreadPool(1);

    }

    @Override
    public IBinder onBind(Intent intent) {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    plates = (ArrayMap<String, Plate>) msg.obj;
                    if (null != callBack) {
                        callBack.getData(plates);
                    }
                }

            }
        };

        runService();

        return new MyBinder();
    }

    private void runService() {
        Runnable getData_Task = new Runnable() {
            @Override
            public void run() {

                PlateDAO plateDAO = new PlateDAO();
                ArrayMap<String, Plate> platesArrayMap = plateDAO.getPlates();

                Message msg = Message.obtain();
                msg.obj = platesArrayMap;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(getData_Task, 2, 2, TimeUnit.SECONDS);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public class MyBinder extends Binder {
        public Service_refrashWeight getService() {
            return Service_refrashWeight.this;
        }
    }

    public void setServiceCallBack(ServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public ServiceCallBack getServiceCallBack() {

        return this.callBack;
    }

    public interface ServiceCallBack {
        void getData(ArrayMap<String, Plate> plateList);
    }

}
