package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.phils.Admin.Add_Stock_Type_Activity;
import com.example.phils.Admin.Assign_user_Job_Activity;
import com.example.phils.Admin.LoginActivity;
import com.example.phils.Admin.Update_JobList_Activity;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.CategorySpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobList_Update extends AppCompatActivity {
    RequestQueue requestQueue;
    AppConfig appConfig;
    ArrayList<ProjectManagerSpinner> projectManagerList = new ArrayList<>();
    ArrayAdapter<ProjectManagerSpinner> projectManagerAdapter;
    Dialog dialog;
    TextView jobCategory,jobsize,projectmanager,status_check;
    String url = "https://mployis.com/staging/api/job/job_project_manager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_update);
        requestQueue = Volley.newRequestQueue(this);
        appConfig = new AppConfig(this);

        projectmanager = findViewById(R.id.projectmanager);

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        //Toast.makeText(this, token +userId +location, Toast.LENGTH_SHORT).show();



        StringRequest request3 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_project_manager",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            String user_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(JobList_Update.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(JobList_Update.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String user_id = object.getString("user_id");
                                    String user_full_name = object.getString("user_full_name");

                                    user_status = object.getString("user_status");
                                    if (user_status.equals(String.valueOf(1))) {


                                        projectManagerList.add(new ProjectManagerSpinner(user_id,user_full_name));
                                        projectManagerAdapter = new ArrayAdapter<>(JobList_Update.this,
                                                android.R.layout.simple_spinner_dropdown_item,projectManagerList);
                                        projectManagerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    }
                                }
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobList_Update.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //return super.getHeaders();
            }
        };

        RequestQueue requestQueue3 = Volley.newRequestQueue(JobList_Update.this);
        requestQueue3.add(request3);

        projectmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                // Initialize dialog
                dialog = new Dialog(JobList_Update.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(750, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(projectManagerAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        projectManagerAdapter.getFilter().filter(charSequence);
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }

                                                }

                );


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView

                        ProjectManagerSpinner sp = (ProjectManagerSpinner) parent.getItemAtPosition(position);
                        projectmanager.setText(sp.user_full_name);


                        Toast.makeText(JobList_Update.this, sp.user_id, Toast.LENGTH_SHORT).show();                        // Dismiss dialog
                        dialog.dismiss();


                    }
                });
            }
        });

    }
}