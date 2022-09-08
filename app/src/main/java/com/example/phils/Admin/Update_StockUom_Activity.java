package com.example.phils.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Update_StockUom_Activity extends AppCompatActivity {
    EditText uom_name_add;
    TextView stock_size_status;
    ArrayList<String> arrayList1;
    Dialog dialog;
    Button btn;
    TextView location_save;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_uom);
        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        uom_name_add = findViewById(R.id.uom_name_add);
        stock_size_status= findViewById(R.id.stock_size_status);
        String nameuom = getIntent().getStringExtra("name");
        String statusuom = getIntent().getStringExtra("status");
        uom_name_add.setText(nameuom);
        stock_size_status.setText(statusuom);
        btn = findViewById(R.id.uom_insert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();
            }
        });

        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        stock_size_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Update_StockUom_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1=dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_StockUom_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
                listViewstatus.setAdapter(adapter);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        stock_size_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
        // end Status spinner
    }
    private void Insert() {
        String e1 =  uom_name_add.getText().toString().toUpperCase(Locale.ROOT).trim();
        String e3 = stock_size_status.getText().toString().trim();
        if(TextUtils.isEmpty(e1))
        {
            uom_name_add.setError("Enter uom Name");
            Toast.makeText(Update_StockUom_Activity.this, "Enter UOM Name", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(e3))
        {
            Toast.makeText(Update_StockUom_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {

            if(e3.equals("Enable"))
            {
                e3 = "1";
            }
            else
            {
                e3 = "0";
            }

            String e4 =  e1.toUpperCase(Locale.ROOT);
            String e6 = e3;
            String id = getIntent().getStringExtra("id");

            StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_category/update_stock_uom.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Update_StockUom_Activity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), StockUomActivity.class));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Update_StockUom_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("uom_id",id);
                    params.put("uom_name",e4);
                    params.put("uom_status",e6);
                    return  params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Update_StockUom_Activity.this);
            requestQueue.add(request);

        }
    }
}