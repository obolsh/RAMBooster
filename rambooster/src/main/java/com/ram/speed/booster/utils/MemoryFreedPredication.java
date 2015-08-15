package com.ram.speed.booster.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Debug.MemoryInfo;
import android.text.TextUtils;

import java.util.List;

/**
 * @author Pratik Poat(prtkpopat@yahoo.com)
 * @info calculate memory freed predication of processes.
 * *
 */
public class MemoryFreedPredication {

    private ActivityManager activityManager;

    private static MemoryFreedPredication INSTANCE;

    /**
     * gives object(singleton) of MemoryFreePredication
     */
    public static MemoryFreedPredication getInstance(Context context) {
        if (INSTANCE != null)
            return INSTANCE;
        else
            return INSTANCE = new MemoryFreedPredication(context);
    }

    /**
     * Constructor
     */
    private MemoryFreedPredication(Context context) {
        activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * get process ids of give package name. if package name is null then returns all pids
     */
    private int[] getPids(String packageName) {
        List<RunningAppProcessInfo> pInfos = activityManager
                .getRunningAppProcesses();
        int pids[] = new int[pInfos.size()];
        if (TextUtils.isEmpty(packageName)) {
            for (int i = 0; i < pInfos.size(); i++) {
                pids[i] = pInfos.get(i).pid;

            }
        } else {
            for (int i = 0; i < pInfos.size(); i++) {
                if (pInfos.get(i).processName.equalsIgnoreCase(packageName))
                    pids[i] = pInfos.get(i).pid;

            }
        }
        return pids;
    }

    /**
     * calculates memory predication of given process ids
     */
    public int calculateMemoryUsage(int pid) {

        MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});

        int totalMemory = 0;
        for (MemoryInfo info : memoryInfo) {
            totalMemory += (info.dalvikPss + info.nativePss + info.otherPss);
        }

        return totalMemory * 1024;
    }

    /**
     * formats given file size in B/KB/MB/GB
     * fileSize in bytes**
     */
    public static String formatFileSize(String fileSize) {

        String[] sizes = {"B", "KB", "MB", "GB"};
        double len = Double.parseDouble(fileSize);
        int order = 0;

        if (len < 1024) {
            return String.format("%s %s", 1, sizes[1]);
        } else {
            while (len >= 1024 && order + 1 < sizes.length) {
                order++;
                len = len / 1024;
            }
        }

        len = Math.round(len * 100.0) / 100.0;
        return String.format("%s %s", len, sizes[order]);
    }

}
