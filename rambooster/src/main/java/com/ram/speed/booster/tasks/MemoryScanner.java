package com.ram.speed.booster.tasks;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ram.speed.booster.RAMBooster;
import com.ram.speed.booster.interfaces.ScanListener;
import com.ram.speed.booster.utils.Constants;
import com.ram.speed.booster.utils.MemoryFreedPredication;
import com.ram.speed.booster.utils.ProcessInfo;
import com.ram.speed.booster.utils.Utils;
import java.util.ArrayList;
import java.util.List;


public class MemoryScanner implements Runnable,Constants {

    private  Context context;
    private ScanListener listener;

    public MemoryScanner(Context context, ScanListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void run() {
        if (RAMBooster.isDEBUG())
        Log.d(TAG, "Scanner started...");
        listener.onStarted();
        ActivityManager activityManager = (ActivityManager)   context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos =
                filterProcesses(activityManager.getRunningAppProcesses());
        List<ProcessInfo> appProcessesInfos = createProcessInfosFromRunningApps(runningAppProcessInfos);
        RAMBooster.setAppProcessInfos(appProcessesInfos);

        long availableRam = Utils.calculateAvailableRAM(context)/weight;
        long totalRam=Utils.calculateTotalRAM()/weight;

        listener.onFinished(availableRam,totalRam,appProcessesInfos);
        if (RAMBooster.isDEBUG())
        Log.d(TAG, "Scanner finished");

    }


    private List<ActivityManager.RunningAppProcessInfo> filterProcesses(
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos) {

        List<ActivityManager.RunningAppProcessInfo> result = new ArrayList<ActivityManager
                .RunningAppProcessInfo>();

        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfos) {
            try {
                ApplicationInfo applicationInfo = getApplicationInfo(processInfo.processName);
                if (RAMBooster.isDEBUG())
                    Log.d(TAG, "Scanner founded process: "+applicationInfo.packageName);
                // remove system processes
                if (!RAMBooster.mShouldCleanSystemApps())
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) continue;
                // remove SpeedChecker app process
                if (applicationInfo.packageName.equals(context.getPackageName())) continue;

                result.add(processInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

        return result;
    }

    private ApplicationInfo getApplicationInfo(String packageName) throws PackageManager
            .NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(packageName, 0);
    }
    private List<ProcessInfo> createProcessInfosFromRunningApps(List<ActivityManager
            .RunningAppProcessInfo>  processInfos) {
        MemoryFreedPredication predication = MemoryFreedPredication.getInstance(context);

        List<ProcessInfo> processes = new ArrayList<ProcessInfo>();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            ProcessInfo process = new ProcessInfo(processInfo);
            process.setMemoryUsage(predication.calculateMemoryUsage(processInfo.pid));

            processes.add(process);
        }

        return processes;
    }
}
