package com.ram.speed.booster.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.ram.speed.booster.RAMBooster;
import com.ram.speed.booster.interfaces.CleanListener;
import com.ram.speed.booster.interfaces.ScanListener;
import com.ram.speed.booster.tasks.CleanTask;
import com.ram.speed.booster.tasks.MemoryScanner;
import com.ram.speed.booster.utils.Constants;


public class LightService extends IntentService implements Constants{

    public static boolean alreadyRunning = false;

    public LightService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getAction().equals(ACTION_SCAN)){
            if (RAMBooster.isDEBUG())
            Log.d(TAG, "Start scanning task");
            ScanListener listener = RAMBooster.getScanListener();
                if (listener!=null)
                new Thread(new MemoryScanner(getApplicationContext(),listener )).start();
            else {
                    if (RAMBooster.isDEBUG())
                        Log.d(TAG, "Cannot start scanning task, listener is empty. Skip");
                }

        } else if (intent.getAction().equals(ACTION_CLEAN)){
            if (RAMBooster.isDEBUG())
                Log.d(TAG, "Start cleaning task");
            CleanListener listener = RAMBooster.getCleanListener();
            if (listener!=null)
                new Thread(new CleanTask(getApplicationContext(),
                        RAMBooster.getAppProcessInfos(), listener)).start();
            else {
                if (RAMBooster.isDEBUG())
                    Log.d(TAG, "Cannot start cleaning task, listener is empty. Skip");
            }
        }
        stopSelf();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alreadyRunning = true;
    }

    @Override
    public void onDestroy() {
        alreadyRunning = false;
        if (RAMBooster.isDEBUG())
            Log.d(TAG, "Service disabled");
        super.onDestroy();

    }

}
