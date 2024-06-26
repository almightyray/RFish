package com.example.rfishx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardUser extends AppCompatActivity {

    private Button btn_LokasiPenangkapan;
    private Button btn_DataHasilTangkapan; // tambahkan variabel tombol baru
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        // Initialize views
        btn_LokasiPenangkapan = findViewById(R.id.btn_LokasiPenangkapan);
        btn_DataHasilTangkapan = findViewById(R.id.btn_DataHasilTangkapan);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        // Check permissions
        checkPermissions();

        // Set welcome message
        setWelcomeMessage();

        // Button click listener
        btn_LokasiPenangkapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardUser.this, LokasiPenangkapan.class);
                startActivity(i);
            }
        });
        btn_DataHasilTangkapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardUser.this, DataHasilTangkapan.class);
                startActivity(i);
            }
        });
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, 1234);
        }
    }

    private void setWelcomeMessage() {
        // Get username from intent extras
        String username = getIntent().getStringExtra("username");
        // Set welcome message with username
        if (username != null && !username.isEmpty()) {
            String message ="Selamat Datang";
            welcomeTextView.setText(message);
        } else {
            // Default welcome message if username is not available
            welcomeTextView.setText("Selamat datang!");
        }
    }
}


