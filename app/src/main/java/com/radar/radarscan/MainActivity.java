package com.radar.radarscan;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.radar.widget.RadarScanView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.root);
        MaterialButton button = findViewById(R.id.btn);
        RadarScanView radarScanView = findViewById(R.id.radar_scan);

        radarScanView.setCanClickToStart(true)
                .setOnScanClickListener(view -> {

        });

        button.setOnClickListener(view -> {
            if (radarScanView.getScanState()) {
                radarScanView.stopScan();
            } else {
                radarScanView.startScan();
            }
        });
    }
}