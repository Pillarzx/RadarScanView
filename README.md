# RadarScanView
Android RadarScanView , An easy-to-use view, can be used for any scanning operation view.  雷达扫描视图

[![](https://jitpack.io/v/Pillarzx/RadarScanView.svg)](https://jitpack.io/#Pillarzx/RadarScanView)

# Preview

<img src="https://github.com/Pillarzx/RadarScanView/blob/main/img/screen2.gif" width="240" height="360">

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
	        implementation 'com.github.Pillarzx:RadarScanView:1.2.0'
	}
```



# Usage

**XML**:

```xml
<com.radar.widget.RadarScanView
        android:id="@+id/radar_scan"
        android:layout_width="120dp"
        android:layout_height="120dp"
        app:circleColor="#8aa8ff"  
        app:circleWidth="1"
        app:tailColor="#8aa8ff"
        app:radarColor="#99a2a2a2"
        app:innerRingAlpha="94"
        app:outerRingAlpha="64"
        app:innerRingWidth="6"
        app:outerRingWidth="4"/>
```



|      Name      |                 Value                  |
| :------------: | :------------------------------------: |
|  circleColor   |        color for  radar circle         |
|  circleWidth   |         width for radar circle         |
|   tailColor    |        tail color for scan line        |
|   radarColor   |         center color for radar         |
| innerRingAlpha | Alpha for the first ring out of radar  |
| outerRingAlpha | Alpha for the second ring out of radar |
| innerRingWidth | width for the first ring out of radar  |
| outerRingWidth | width for the second ring out of radar |
|  commonSpeed   |          speed for scan line           |
|   ringSpeed    |             speed for ring             |



**Java**:

```java
RadarScanView radarScanView = findViewById(R.id.radar_scan);

radarScanView.setCanClickToStart(true); //Is it possible to start the animation by clicking the view
radarScanView.getScanState(); //Get the scan state of the animation
radarScanView.startScan(); //Start to scan
radarScanView.stopScan(); //Stop to scan
radarScanView.setOnScanClickListener(view -> { /*TODO*/ })  
radarScanView.setRingSpeed(5); //set ring speed（recommend range 1-30）
radarScanView.setCommonSpeed(3); //set scan line speed（recommend range 1-30）
radarScanView
    .setCircleColor("#FFCDDC39")
	.setCircleWidth(2)
    .setTailColor("#FFF3DB0F")
    .setRadarColor("#FFCDDC39")
    .setInnerRingStrokeWidth(6)
    .setInnerRingStrokeAlpha(94)
    .setOuterRingStrokeWidth(5)
    .setOuterRingStrokeAlpha(94)
    .build(); 

```



# Change log

- **V1.0.0**  2020.11.20  

  Release the first edition

- **V1.1.0**  2020.11.24

  1. Add some new XML attrbutes.
  2. Add new methods for the class.
  
- **V1.2.0** 2020.11.27

  1. Add speed attributes
  2. Adjust animation logic



