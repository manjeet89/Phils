package com.example.phils.Admin;

import androidx.annotation.Nullable;
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
import com.example.phils.Spinner.UserLocationSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectLocationActivity extends AppCompatActivity {

    TextView select_location,setlocation;
    Dialog dialog;
    Button insert_location;
    ArrayList<UserLocationSpinner> locationList = new ArrayList<>();
    ArrayAdapter<UserLocationSpinner> locationAdapter;
    RequestQueue requestQueue;
    String url = "https://mployis.com/staging/api/job/location_list";
    AppConfig appConfig;
    TextView location_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_location);
        requestQueue = Volley.newRequestQueue(this);
        appConfig = new AppConfig(this);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);
        setlocation = findViewById(R.id.setlocation);

        insert_location = findViewById(R.id.insert_location);
        insert_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveLocation();
            }
        });


        select_location=findViewById(R.id.select_location);
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocation();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String stock_category_status;
                            String emp_type_name;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(ProjectLocationActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(ProjectLocationActivity.this,MainActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String location_id = object.getString("location_id");
                                    String location_name = object.getString("location_name");

                                    locationList.add(new UserLocationSpinner(location_id,location_name));
                                    locationAdapter = new ArrayAdapter<UserLocationSpinner>(ProjectLocationActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,locationList);
                                    locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(ProjectLocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(ProjectLocationActivity.this);
        requestQueue.add(request);


        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(ProjectLocationActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(750,1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);


                // set adapter
                listView.setAdapter(locationAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        locationAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView

                        UserLocationSpinner sp = (UserLocationSpinner)parent.getItemAtPosition(position);
                        select_location.setText(sp.location_name);
                        setlocation.setText(sp.location_id);
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    private void SaveLocation()
    {
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocation();
        String locationName = setlocation.getText().toString();

        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/set_location",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(ProjectLocationActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Location updated successfully"))
                            {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String location_name = jsonObject1.getString("location_name");
                                 appConfig.SaveLocation(location_name);
                                startActivity(new Intent(ProjectLocationActivity.this, MainActivity.class));

                                //String setlocation = appConfig.getLocation();
                                //location_save.setText(setlocation);


                            }else {
                                Toast.makeText(ProjectLocationActivity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProjectLocationActivity.this, MainActivity.class));
                                finish();
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProjectLocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                params.put("project_location_id", locationName);
                params.put("user_id", userId);
                return  params;
            }

        };
        RequestQueue  requestQueue1 = Volley.newRequestQueue(ProjectLocationActivity.this);
        requestQueue1.add(request1);
    }
}