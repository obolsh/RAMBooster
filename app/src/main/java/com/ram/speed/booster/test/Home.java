package com.ram.speed.booster.test;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ram.speed.booster.RAMBooster;
import com.ram.speed.booster.interfaces.CleanListener;
import com.ram.speed.booster.interfaces.ScanListener;
import com.ram.speed.booster.utils.ProcessInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Home extends Activity {
    private RAMBooster booster;
    private String TAG="Booster.Test";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booster==null)
                    booster=null;
                booster = new RAMBooster(Home.this);
                booster.setDebug(true);
                booster.setScanListener(new ScanListener() {
                    @Override
                    public void onStarted() {
                        Log.d(TAG, "Scan started");
                    }

                    @Override
                    public void onFinished(long availableRam, long totalRam, List<ProcessInfo> appsToClean) {

                        Log.d(TAG, String.format(Locale.US,
                                "Scan finished, available RAM: %dMB, total RAM: %dMB",
                                availableRam,totalRam));
                        List<String> apps = new ArrayList<String>();
                        for (ProcessInfo info:appsToClean){
                            apps.add(info.getProcessName());
                        }
                        Log.d(TAG, String.format(Locale.US,
                                "Going to clean founded processes: %s", Arrays.toString(apps.toArray())));
                        booster.startClean();
                    }
                });
                booster.setCleanListener(new CleanListener() {
                    @Override
                    public void onStarted() {
                        Log.d(TAG, "Clean started");
                    }


                    @Override
                    public void onFinished(long availableRam, long totalRam) {

                        Log.d(TAG, String.format(Locale.US,
                                "Clean finished, available RAM: %dMB, total RAM: %dMB",
                                availableRam,totalRam));
                        booster = null;

                    }
                });
                booster.startScan(true);
            }
        });
    }

}
