package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phils.Shareprefered.AppConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConsumptionDetailActivity extends AppCompatActivity {
    TextView tvDate,tvDate1;
    TextView location_save;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_detail);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        tvDate = findViewById(R.id.tvDate);
        tvDate1 = findViewById(R.id.tvDate1);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                updateCalender();
            }
            private  void  updateCalender()
            {
                String Format = "dd/MM/YY";
                SimpleDateFormat sdf = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sdf = new SimpleDateFormat(Format, Locale.US);
                }
                tvDate.setText(sdf.format(calendar.getTime()));
            }
        };
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ConsumptionDetailActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Calendar calendar1 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(Calendar.YEAR,i);
                calendar1.set(Calendar.MONTH,i1);
                calendar1.set(Calendar.DAY_OF_MONTH,i2);

                updateCalender();
            }
            private  void  updateCalender()
            {
                String Format = "dd/MM/YY";
                SimpleDateFormat sdf = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sdf = new SimpleDateFormat(Format, Locale.US);
                }
                tvDate1.setText(sdf.format(calendar1.getTime()));
                String tv = tvDate1.getText().toString();
                Toast.makeText(ConsumptionDetailActivity.this, tv, Toast.LENGTH_SHORT).show();
            }
        };
        tvDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ConsumptionDetailActivity.this,date1,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
}