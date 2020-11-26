package com.radar.radarscan;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.radar.widget.RadarScanView;

public class MainActivity extends AppCompatActivity {

    private int mSpeed=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.root);
        MaterialButton button = findViewById(R.id.btn);
        MaterialButton button2 = findViewById(R.id.btn2);
        MaterialButton button3 = findViewById(R.id.btn3);
        RadarScanView radarScanView = findViewById(R.id.radar_scan);

        radarScanView.setCanClickToStart(true)
                .setOnScanClickListener(view -> { /*todo*/});

        button.setOnClickListener(view -> {
            if (radarScanView.getScanState()) {
                radarScanView.stopScan();
            } else {
                radarScanView.startScan();
            }
        });
        button2.setOnClickListener(view -> {
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
        });
        button3.setOnClickListener(view -> {
            radarScanView.setRingSpeed(mSpeed--);
        });
    }
}