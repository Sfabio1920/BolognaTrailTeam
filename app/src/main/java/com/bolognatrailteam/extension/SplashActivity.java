package com.bolognatrailteam.extension;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.bolognatrailteam.ui.BolognaTrailTeam;

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, BolognaTrailTeam.class));
        finish();
    }
}