package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Stock_Category_Activity extends AppCompatActivity implements

     AdapterView.OnItemSelectedListener {
        String[] country = { "Grinding", "Welding", "Other"};
        String[] status = {"Enable","Disable"};

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_category);
            Spinner spin = (Spinner) findViewById(R.id.spinner);
            Spinner  spin_status = (Spinner) findViewById(R.id.spinner_status);
            spin_status.setOnItemSelectedListener(this);
            spin.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            ArrayAdapter ss = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
            ss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Setting the ArrayAdapter data on the Spinner
            spin.setAdapter(aa);
            spin_status.setAdapter(ss);
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String j = country[i];
            String k = "Grinding";
            String p = "Welding";
//            String a = "Enable";
//            String b = "Disable";

            if(k.equals(j))
            {
                j = "4";
            }
           else if(p.equals(j))
            {
                j="5";
            }
//            else if(a.equals(j))
//            {
//                j="1";
//            }
//            else if(b.equals(j))
//            {
//                j="0";
//            }
            else {
                j="0";
            }

        Toast.makeText(getApplicationContext(),j , Toast.LENGTH_LONG).show();

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}