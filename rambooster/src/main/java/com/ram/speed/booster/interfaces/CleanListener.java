package com.ram.speed.booster.interfaces;


public interface CleanListener {
    void onStarted();
    void onFinished(long availableRam, long totalRam);
}
