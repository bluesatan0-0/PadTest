package com.acuit.yanj.padtest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.ArrayMap;

import com.acuit.yanj.padtest.Base.BaseHandler;
import com.acuit.yanj.padtest.Bean.Dish;
import com.acuit.yanj.padtest.Bean.Hole;
import com.acuit.yanj.padtest.Bean.Line;
import com.acuit.yanj.padtest.Bean.Plate;
import com.acuit.yanj.padtest.Model.MainBusiness.MainBusiness_DataLoad;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Service_refrashWeight extends Service {

    private ScheduledExecutorService scheduledExecutorService;
    private BaseHandler uiHandler;

    public Service_refrashWeight() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        scheduledExecutorService = Executors.newScheduledThreadPool(1);

    }

    @Override
    public IBinder onBind(Intent intent) {

        uiHandler = (BaseHandler) intent.getSerializableExtra("UIHandler");

        runService();

        return new Binder();
    }

    private void runService() {
        Runnable getData_Task = new Runnable() {
            @Override
            public void run() {

                MainBusiness_DataLoad mainBusiness_dataLoad = new MainBusiness_DataLoad();

                mainBusiness_dataLoad.setOnDataLoadedLisener(new MainBusiness_DataLoad.OnDataLoadedLisener() {
                    @Override
                    public void load_Lines_Holes_Plates(List<Line> linesList, List<Hole> holesList, ArrayMap<String, Plate> plateList, List<Dish> dishesList) {
                        Message msg = Message.obtain();
                        msg.obj = plateList;
                        msg.what = 1;
                        uiHandler.sendMessage(msg);
                    }
                });


            }
        };

        scheduledExecutorService.schedule(getData_Task, 2, TimeUnit.SECONDS);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

//    public MyBinder

}
