package com.ram.speed.booster.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Utils {


    public static long calculateTotalRAM() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;

        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();//meminfo
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {

            }
            //total Memory
            initial_memory = Long.valueOf(arrayOfString[1]).longValue() * (long) 1024;
            localBufferedReader.close();
            return initial_memory;
        } catch (IOException e) {
            return -1;
        }
    }

    public static long calculateAvailableRAM(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity
                .ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        return mi.availMem;
    }
}
