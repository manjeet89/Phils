package com.example.phils.Rought;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.phils.R;

public class Demo extends AppCompatActivity {

    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        view = findViewById(R.id.name);
        String name = getIntent().getStringExtra("username");
        view.setText(name);
    }
}