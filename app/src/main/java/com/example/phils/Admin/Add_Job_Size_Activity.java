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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Job_Size_Activity extends AppCompatActivity {

    TextView check_status;
    ArrayList<String> arrayList1;
    Dialog dialog;
    Button button;
    EditText category_name;
    TextView location_save;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_size);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        check_status = findViewById(R.id.status_check);
        button = findViewById(R.id.insert_job_cat);
        category_name = findViewById(R.id.category_insert);

        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Insert();
            }
        });
        check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Add_Job_Size_Activity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Add_Job_Size_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        check_status.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
    }
    private void Insert() {
        String e2 = category_name.getText().toString().trim();
        String e3 = check_status.getText().toString().trim();

        if(TextUtils.isEmpty(e2))
        {
            category_name.setError("Please Enter your Category Name");
            Toast.makeText(Add_Job_Size_Activity.this, "Please Enter your Size Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(e3))
        {
            check_status.setError("Please Select your Status");
            Toast.makeText(Add_Job_Size_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
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

            String e5 = e2;
            String e6 = e3;
//            Toast.makeText(this, e4+"  /  "+e5+"  /  "+e6, Toast.LENGTH_SHORT).show();


            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");

            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/add_job_size",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                Toast.makeText(Add_Job_Size_Activity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),Job_Size_Activity.class));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Job_Size_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token",token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("job_size_name",e5);
                    params.put("job_size_status",e6);
                    return  params;
                }


            };
            RequestQueue  requestQueue = Volley.newRequestQueue(Add_Job_Size_Activity.this);
            requestQueue.add(request);

        }
    }
}