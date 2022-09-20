package com.example.phils;

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
import com.example.phils.Admin.Add_Job_Category_Activity;
import com.example.phils.Admin.Job_Category_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_JobCategory_Activity extends AppCompatActivity {

    EditText job_name;
    TextView statusJob;
    Button button;
    Dialog dialog;
    ArrayList<String> arrayList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job_category);

        job_name = findViewById(R.id.job_name);
        statusJob = findViewById(R.id.statusJob);
        button = findViewById(R.id.insert_job_cat);

        String name = getIntent().getStringExtra("Jobname");
        String jobstatus = getIntent().getStringExtra("jobstatus");
        job_name.setText(name);
        statusJob.setText(jobstatus);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Insert();
            }
        });

        arrayList1=new ArrayList<>();
        arrayList1.add("Enable");
        arrayList1.add("Disable");

        statusJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_JobCategory_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                // set custom height and width
                dialog.getWindow().setLayout(650, 800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText1 = dialog.findViewById(R.id.edit_text1);
                ListView listViewstatus = dialog.findViewById(R.id.list_view_status);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Update_JobCategory_Activity.this, android.R.layout.simple_list_item_1, arrayList1);
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
                        statusJob.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });
    }
    private void Insert() {
        String jobname = job_name.getText().toString();
        String jobstatus = statusJob.getText().toString();
        if (TextUtils.isEmpty(jobname)) {
            job_name.setError("Please Enter your Category Name");
            Toast.makeText(Update_JobCategory_Activity.this, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(jobstatus)) {
            statusJob.setError("Please Select your Status");
            Toast.makeText(Update_JobCategory_Activity.this, "Please Select your Status", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (jobstatus.equals("Enable")) {
                jobstatus = "1";
            } else {
                jobstatus = "0";
            }
            String Job = jobstatus;


            String token = getIntent().getStringExtra("token");
            String userId = getIntent().getStringExtra("userId");
            String location = getIntent().getStringExtra("location");
            String id = getIntent().getStringExtra("id");

            StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/update_job_category",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                Toast.makeText(Update_JobCategory_Activity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Update_JobCategory_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("user_token", token);
                    headers.put("user_id", userId);
                    headers.put("project_location_id", location);

                    return headers;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("job_category_name", jobname);
                    params.put("job_category_status", Job);
                    params.put("job_category_id", id);

                    return params;
                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(Update_JobCategory_Activity.this);
            requestQueue.add(request);
        }
    }
}