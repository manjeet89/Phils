package com.example.phils.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;

public class ReportsActivity extends AppCompatActivity {
    TextView location_save;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

    }
}