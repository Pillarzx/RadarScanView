# RadarScanView
Android RadarScanView , An easy-to-use view, can be used for any scanning operation view.  雷达扫描视图



# Preview

![screen](https://github.com/Pillarzx/RadarScanView/blob/master/img/screen.gif)

# How to

To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```groovy
	dependencies {
	        implementation 'com.github.Pillarzx:RadarScanView:1.0.0'
	}
```



# Usage

XML:

```xml
<com.radar.widget.RadarScanView
        android:id="@+id/radar_scan"
        android:layout_width="120dp"
        android:layout_height="120dp"
        app:circleColor="#8aa8ff"
        app:circleWidth="1"
        app:tailColor="#8aa8ff" />
```



Java:

```java
RadarScanView radarScanView = findViewById(R.id.radar_scan);

radarScanView.setCanClickToStart(true); //Is it possible to start the animation by clicking the view
radarScanView.getScanState(); //Get the scan state of the animation
radarScanView.startScan(); //Start to scan
radarScanView.stopScan(); //Stop to scan

radarScanView.setOnScanClickListener(view -> { /*TODO*/ })

```



# Change log

- **V1.0.0**  2020.11.20  

  Release the first edition

