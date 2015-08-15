package com.ram.speed.booster.utils;

import android.app.ActivityManager;

public class ProcessInfo {
    private  int pid;
    private int memoryUsage;
    private  String processName;

    public ProcessInfo(ActivityManager.RunningAppProcessInfo processInfo) {
        this.pid = processInfo.pid;
        this.processName = processInfo.processName;
    }

    public long getSize() {
        return memoryUsage;
    }

    public void setMemoryUsage(int memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public String getProcessName() {
        return processName;
    }

    public int getPid() {
        return pid;
    }

}
