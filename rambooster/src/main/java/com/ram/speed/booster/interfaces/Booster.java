package com.ram.speed.booster.interfaces;

public interface Booster {

    void setScanListener (ScanListener listener);
    void setCleanListener (CleanListener listener);
    void startScan(boolean isSystem);
    void startClean();
    void setDebug (boolean isDebug);

}
