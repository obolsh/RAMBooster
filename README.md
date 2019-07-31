# RAMBooster
Android RAMBooster

## Setup is easy:
* **put this permissions into manifest:**

`<uses-permission android:name="android.permission.GET_PACKAGE_SIZE" />`

`<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />`

* **add this service into application tag in manifest:**

`<service android:name="com.ram.speed.booster.services.LightService" />`

* **add this line into your dependencies for Gradle:**

```
dependencies {
       compile 'com.github.obolsh:rambooster:1.0.2@aar'
}
```

   or this for Maven:

   ```
   <dependency>
     <groupId>com.github.obolsh</groupId>
     <artifactId>rambooster</artifactId>
     <version>1.0.2</version>
     <type>aar</type>
   </dependency>
   ```

* **rebuild your project**

## USAGE
Now you are able to launch RAM Booster. Add this lines into your code:

* **Create RAM Booster instance:**

`RAMBooster booster = new RAMBooster(context);`

* **setup scan listener:**

```
booster.setScanListener(new ScanListener() {
       @Override
       public void onStarted() {
       }
       
       @Override
       public void onFinished(long availableRam, long totalRam, List<ProcessInfo> appsToClean) {
       }
});
```

* **setup clean listener:**

```
booster.setCleanListener(new CleanListener() {
       @Override
       public void onStarted() {
       }
       
       @Override
       public void onFinished(long availableRam, long totalRam) {
       }
});
```

* **start scanning, set true if apps marked as system have to be also cleaned:**

`booster.startScan(true);`

* **launch clean after scanning completed:**

`booster.startClean();`

* **enable log if you need to:**

`booster.setDebug(true);`

## RAMBooster supports API LEVEL 8+

## Copyright and Licensing

This library is distributed under an Apache 2.0 License.

