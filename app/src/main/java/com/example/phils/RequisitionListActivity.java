package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RequisitionListActivity extends AppCompatActivity {

    Button add_reqlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_list);
        add_reqlist = findViewById(R.id.add_reqlist);
        add_reqlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RequisitionAddActivity.class));
            }
        });
    }
}