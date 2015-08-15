package com.ram.speed.booster;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.ram.speed.booster.interfaces.Booster;
import com.ram.speed.booster.interfaces.CleanListener;
import com.ram.speed.booster.interfaces.ScanListener;
import com.ram.speed.booster.services.LightService;
import com.ram.speed.booster.utils.Constants;
import com.ram.speed.booster.utils.ProcessInfo;
import java.util.List;

/***
 * Entry point for RAM Booster
 */
public class RAMBooster implements Booster,Constants{

    private static ScanListener mScanListener;
    private static CleanListener mCleanListener;
    private Context context;
    private static List<ProcessInfo> appProcessInfos;
    private static boolean DEBUG = false;
    private static boolean shouldCleanSystemApps = false;


    public static boolean mShouldCleanSystemApps() {
        return shouldCleanSystemApps;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    /***
     * enables log
     * @param isDebug - set true if you need log about processing
     */
    @Override
    public void setDebug(boolean isDebug) {
        DEBUG = isDebug;
    }


    public RAMBooster(Context context) {
        appProcessInfos=null;
        mScanListener=null;
        mCleanListener=null;
        shouldCleanSystemApps=false;
        DEBUG=false;
        this.context = context;
    }

    public static List<ProcessInfo> getAppProcessInfos() {
        return appProcessInfos;
    }

    public static void setAppProcessInfos(List<ProcessInfo> appProcessInfos) {
        RAMBooster.appProcessInfos = appProcessInfos;
    }

    /***
     * set listener for scanning process
     * @param listener
     */
    @Override
    public void setScanListener(ScanListener listener) {
        mScanListener = listener;
    }

    public static ScanListener getScanListener() {
        return mScanListener;
    }
    /***
     * set listener for cleaning process
     * @param listener
     */
    @Override
    public void setCleanListener(CleanListener listener) {
        mCleanListener = listener;
    }

    public static CleanListener getCleanListener() {
        return mCleanListener;
    }

    /***
     * start scanning
     * @param isSystem - set true is all founded processes should be cleaned,
     *                 including marked as SYSTEM
     */
    @Override
    public void startScan(boolean isSystem) {
        if (!LightService.alreadyRunning){
            shouldCleanSystemApps = isSystem;
            Intent serviceIntent = new Intent(context, LightService.class);
            serviceIntent.setAction(ACTION_SCAN);
            context.startService(serviceIntent);
        } else{
            if (DEBUG)
                Log.d(TAG, "Already Scanning.Skip");
        }

    }

    /***
     * start cleaning after scan
     */
    @Override
    public void startClean() {
        if (!LightService.alreadyRunning){
            if (appProcessInfos!=null){
                Intent serviceIntent = new Intent(context, LightService.class);
                serviceIntent.setAction(ACTION_CLEAN);
                context.startService(serviceIntent);
            } else {
                if (DEBUG)
                    Log.d(TAG, "Cannot start cleaning before scanning.Skip");
            }

        } else{
            if (DEBUG)
                Log.d(TAG, "Already Cleaning.Skip");
        }


    }


}
