package com.example.phils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Admin.Assign_user_Job_Activity;
import com.example.phils.Admin.Job_List_Activity;
import com.example.phils.JobReplaceUseerActivity;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JobReplaceUseerActivity extends AppCompatActivity {

    TextView WR,replace;
    TextView setreplaceuser,setwgreplace;

    Button btn;

    //Woker Grinder and Welder
    boolean[] wokerlenght;
    ArrayList<String> wokerList = new ArrayList<>();
    ArrayList<String> wokerListvalue   = new ArrayList<>();

    static String wokerValue[];
    static String wokerId[];

    RequestQueue requestQueue;
    AppConfig appConfig;
    TextView location_save;


    //Job
    boolean[] joblength;
    ArrayList<String> jobList = new ArrayList<>();
    ArrayList<String> jobListvalue = new ArrayList<>();

    static String jobValue[];
    static String jobId[];

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Job_List_Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_replace_useer);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);
        requestQueue = Volley.newRequestQueue(this);



        btn = findViewById(R.id.insert_type);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String token = getIntent().getStringExtra("token");
                String userId = getIntent().getStringExtra("userId");
                String location = getIntent().getStringExtra("location");
                StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/job_replace_user",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("message");

                                    Toast.makeText(JobReplaceUseerActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Assign_user_Job_Activity.class));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JobReplaceUseerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                        params.put("job_id[]",setwgreplace.getText().toString());
                        params.put("job_emp_user[]",setreplaceuser.getText().toString());
                        return  params;
                    }


                };
                RequestQueue  requestQueue = Volley.newRequestQueue(JobReplaceUseerActivity.this);
                requestQueue.add(request);
            }
        });

        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        WR = findViewById(R.id.WR);
        replace = findViewById(R.id.replace);

        setreplaceuser = findViewById(R.id.setreplaceuser);
        setwgreplace = findViewById(R.id.setwgreplace);

        StringRequest request4 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            wokerValue = new String[jsonArray.length()];
                            wokerId = new String[jsonArray.length()];
                            wokerlenght = new boolean[wokerValue.length];

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                wokerValue[i]=user_name+" - "+user_employee_id;
                                wokerId[i]=user_id;

                            }

//                            Collections.reverse(Arrays.asList(wokerValue));
//                            Collections.reverse(Arrays.asList(wokerId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobReplaceUseerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue4 = Volley.newRequestQueue(JobReplaceUseerActivity.this);
        requestQueue4.add(request4);

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(JobReplaceUseerActivity.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(wokerValue, wokerlenght, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            wokerList.add(wokerId[i]);
                            wokerListvalue.add(wokerValue[i]);

                            Log.d("Nilesh",wokerId[i]);


                            // Sort array list
                            Collections.sort(wokerList);

                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            wokerList.remove(wokerId[i]);
                            wokerListvalue.remove(wokerValue[i]);

                        }
                        Log.d("Nil", String.valueOf(wokerList));
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
//                        StringBuilder stringBuilder = new StringBuilder();
                        String stringBuilder= String.join(",", wokerListvalue);
                        String stringbuilder= String.join(",", wokerList);

                        Log.d("nil",stringBuilder);
                        Log.d("nil",stringbuilder);

                        replace.setText(stringBuilder);
                        setreplaceuser.setText(stringbuilder);

                        Toast.makeText(JobReplaceUseerActivity.this, setreplaceuser.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < wokerlenght.length; j++) {
                            // remove all selection
                            wokerlenght[j] = false;
                            // clear language list
                            wokerListvalue.clear();
                            wokerList.clear();
                            // clear text view value
                            replace.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });




        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            jobValue = new String[jsonArray.length()];
                            jobId = new String[jsonArray.length()];
                            joblength = new boolean[jobValue.length];

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_name = object.getString("user_full_name");

                                jobValue[i]=user_name;
                                jobId[i]=user_id;

                            }

//                            Collections.reverse(Arrays.asList(wokerValue));
//                            Collections.reverse(Arrays.asList(wokerId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobReplaceUseerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(JobReplaceUseerActivity.this);
        requestQueue.add(request);

        WR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(JobReplaceUseerActivity.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(jobValue, joblength, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            jobList.add(jobId[i]);
                            jobListvalue.add(jobValue[i]);

                            Log.d("Nilesh",wokerId[i]);


                            // Sort array list
                            Collections.sort(jobList);

                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            wokerList.remove(jobId[i]);
                            wokerListvalue.remove(jobValue[i]);

                        }
                        Log.d("Nil", String.valueOf(jobList));
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
//                        StringBuilder stringBuilder = new StringBuilder();
                        String stringBuilder= String.join(",", jobListvalue);
                        String stringbuilder= String.join(",", jobList);

                        Log.d("nil",stringBuilder);
                        Log.d("nil",stringbuilder);

                        WR.setText(stringBuilder);
                        setwgreplace.setText(stringbuilder);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < joblength.length; j++) {
                            // remove all selection
                            joblength[j] = false;
                            // clear language list
                            jobListvalue.clear();
                            jobList.clear();
                            // clear text view value
                            replace.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });

    }
}