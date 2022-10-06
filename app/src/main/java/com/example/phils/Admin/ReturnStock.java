package com.example.phils.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReturnStock extends AppCompatActivity {

    TextView category,type,size,quantity;
    EditText assignquantity;
    Button btn;
    AppConfig appConfig;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_stock);
        appConfig = new AppConfig(this);

        category = findViewById(R.id.category);
        type = findViewById(R.id.type);
        size = findViewById(R.id.size);
        quantity= findViewById(R.id.quantity);
        assignquantity = findViewById(R.id.assignquantity);
        btn = findViewById(R.id.btn);

        String CATEGORYset = getIntent().getStringExtra("CATEGORY");
        String TYPEset = getIntent().getStringExtra("TYPE");
        String SIZEset = getIntent().getStringExtra("SIZE");
        String QUANTITYset = getIntent().getStringExtra("QUANTITY");

        category.setText(CATEGORYset);
        type.setText(TYPEset);
        size.setText(SIZEset);
        quantity.setText(QUANTITYset);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

    }

    private void insert() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        String id = getIntent().getStringExtra("id");
        String QUANTITYset = getIntent().getStringExtra("QUANTITY");
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();

        String quantity = assignquantity.getText().toString();
        if(Integer.parseInt(QUANTITYset) >= Integer.parseInt(quantity)){
            StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/requisition/return_stock",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(ReturnStock.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), RequisitionReciverList.class));
                                progressDialog.dismiss();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ReturnStock.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token",token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);
                    headers.put("user_employee_type", user_employee_type);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("assign_id", id);
                    params.put("req_quantity", quantity);
                    return  params;
                }

            };
            RequestQueue requestQueue1 = Volley.newRequestQueue(ReturnStock.this);
            requestQueue1.add(request1);        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(this, "The Quantity field must contain a number less than or equal to"+QUANTITYset, Toast.LENGTH_SHORT).show();
        }
    }
}