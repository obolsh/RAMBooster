package com.ram.speed.booster.interfaces;

import com.ram.speed.booster.utils.ProcessInfo;

import java.util.List;

public interface ScanListener {

    void onStarted();
    void onFinished(long availableRam, long totalRam, List<ProcessInfo> appsToClean);

}
