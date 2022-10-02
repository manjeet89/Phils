package com.example.phils.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.phils.ProjectManagerSpinner;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Spinner.JobCategorySpinner;
import com.example.phils.Spinner.JobSizeSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Update_JobList_Activity extends AppCompatActivity {

//    TextView setcategoryid,setjobsize,setmanager,setconsum,setweldergrinder;
//    TextView jobCategory,jobsize,projectmanager,status_check;
//    EditText seamnumberinsert,jobnumber,jobclint;
//    TextView consumbles_item,worker_w_r;
//
//    Button insert_job;
//    ArrayList<String> arrayList1;
//
//    ArrayList<JobCategorySpinner> jobcategoryList = new ArrayList<>();
//    ArrayList<JobSizeSpinner> jobsizeList = new ArrayList<>();
//    ArrayList<ProjectManagerSpinner> projectManagerList = new ArrayList<>();
//
//    ArrayAdapter<JobCategorySpinner> jobcategoryAdapter;
//    ArrayAdapter<JobSizeSpinner> jobsizeAdapter;
//    ArrayAdapter<ProjectManagerSpinner> projectManagerAdapter;
//
//    RequestQueue requestQueue;
//    Dialog dialog;
//
//    //Woker Grinder and Welder
//    boolean[] wokerlenght;
//    ArrayList<String> wokerList = new ArrayList<>();
//    ArrayList<String> wokerListvalue   = new ArrayList<>();
//
//    static String wokerValue[];
//    static String wokerId[];
//
//
//    //Consumable items
//    boolean[] selectedLanguage;
//    ArrayList<String> langList = new ArrayList<>();
//    ArrayList<String> langListvalue = new ArrayList<>();
//
//    static String listValue[];
//    static String listId[];
//
//    AppConfig appConfig;

    TextView setcategoryid,setjobsize,setmanager,setconsum,setweldergrinder;
    TextView jobCategory,jobsize,projectmanager,status_check;

    EditText seamnumberinsert,jobnumber,jobclint;

    Button insert_job;

    ArrayList<String> arrayList1;

    ArrayList<JobCategorySpinner> jobcategoryList = new ArrayList<>();
    ArrayList<JobSizeSpinner> jobsizeList = new ArrayList<>();
    ArrayList<ProjectManagerSpinner> projectManagerList = new ArrayList<>();

    ArrayAdapter<JobCategorySpinner> jobcategoryAdapter;
    ArrayAdapter<JobSizeSpinner> jobsizeAdapter;
    ArrayAdapter<ProjectManagerSpinner> projectManagerAdapter;


    RequestQueue requestQueue;
    Dialog dialog;

    TextView consumbles_item,worker_w_r;

    //Woker Grinder and Welder
    boolean[] wokerlenght;
    ArrayList<String> wokerList = new ArrayList<>();
    ArrayList<String> wokerListvalue   = new ArrayList<>();

    static String wokerValue[];
    static String wokerId[];


    //Consumable items
    boolean[] selectedLanguage;
    ArrayList<String> langList = new ArrayList<>();
    ArrayList<String> langListvalue = new ArrayList<>();

    static String listValue[];
    static String listId[];

    AppConfig appConfig;

    int p=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job_list);
        requestQueue = Volley.newRequestQueue(this);
        appConfig = new AppConfig(this);
        String token = getIntent().getStringExtra("token");
        String userId = getIntent().getStringExtra("userId");
        String location = getIntent().getStringExtra("location");

        setcategoryid = findViewById(R.id.setcategoryid);
        setjobsize = findViewById(R.id.setjobsize);
        setmanager = findViewById(R.id.setmanager);
        setconsum = findViewById(R.id.setconsum);
        setweldergrinder = findViewById(R.id.setweldergrinder);

        jobCategory = findViewById(R.id.jobCategory);
        jobsize = findViewById(R.id.jobsize);
        projectmanager = findViewById(R.id.projectmanager);
        consumbles_item = findViewById(R.id.consumbles_item);
        worker_w_r = findViewById(R.id.worker_w_r);
        status_check = findViewById(R.id.status_check);

        jobclint = findViewById(R.id.jobclint);
        jobnumber = findViewById(R.id.jobnumber);
        seamnumberinsert = findViewById(R.id.seamnumberinsert);

        String job_name = getIntent().getStringExtra("job_name");
        String job_category_id = getIntent().getStringExtra("job_category_id");
        String job_category_name = getIntent().getStringExtra("job_category_name");
        String job_size_id = getIntent().getStringExtra("job_size_id");
        String job_size_name = getIntent().getStringExtra("job_size_name");
        String job_manager_id = getIntent().getStringExtra("job_manager_id");
        String user_full_name = getIntent().getStringExtra("user_full_name");
        String job_number = getIntent().getStringExtra("job_number");
        String consumables_items = getIntent().getStringExtra("consumables_items");
        String seam_number = getIntent().getStringExtra("seam_number");
        String job_emp_user = getIntent().getStringExtra("job_emp_user");
        String job_status = getIntent().getStringExtra("job_status");

        jobclint.setText(job_name);
        jobnumber.setText(job_number);
        jobCategory.setText(job_category_name);
        setcategoryid.setText(job_category_id);
        jobsize.setText(job_size_name);
        setjobsize.setText(job_size_id);
        projectmanager.setText(user_full_name);
        setmanager.setText(job_manager_id);
        seamnumberinsert.setText(seam_number);

        if(job_status.equals("2"))
        {
            status_check.setText("Completed");
        }
        else
        {
            status_check.setText("In progress");

        }
       // consumbles_item.setText(consumables_items);
       // worker_w_r.setText(job_emp_user);

        String ln = consumables_items;
//        String[] length = ln.split(",");
//        for(String name : length){
//            //System.out.println(name);
//            p++;
//        }
//        Log.d("ok", String.valueOf(p));

        StringRequest request11 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_type",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            listValue = new String[jsonArray.length()];
                            listId = new String[jsonArray.length()];
                            //selectedLanguage = new boolean[listValue.length];

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String emp_type_id = object.getString("stock_type_id");
                                String emp_type_name = object.getString("stock_type_name");

                                String[] length = ln.split(",");
                                for(String name : length){
                                    if(emp_type_id.equals(name))
                                    {
                                        langList.add(emp_type_id);
                                        langListvalue.add(emp_type_name);
                                        Log.d("Nilesh", String.valueOf(langList));
                                        Log.d("Nilesh", String.valueOf(langListvalue));

                                    }
                                    //System.out.println(name);

                                }
                                listValue[i]=emp_type_name;
                                listId[i]=emp_type_id;

                            }
                            String stringBuilder= String.join(",", langListvalue);
                            String stringbuilder = String.join(",",langList);

                            consumbles_item.setText(stringBuilder);
                            setconsum.setText(stringbuilder);

                            Collections.reverse(Arrays.asList(listValue));
                            Collections.reverse(Arrays.asList(listId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue11 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue11.add(request11);

        String WelgerGrinder = job_emp_user;
//        String[] WG = WelgerGrinder.split(",");
//       // String[] length = ln.split(",");
//        for(String name : WG){
//            //System.out.println(name);
//            p++;
//            Log.d("ok", name);
//
//        }
        StringRequest request12 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_welder_grinder_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            wokerId = new String[jsonArray.length()];
                            wokerValue = new String[jsonArray.length()];
                            wokerlenght = new boolean[wokerValue.length];

                            //selectedLanguage = new boolean[listValue.length];

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_id = object.getString("user_id");
                                String user_full_name = object.getString("user_full_name");
                                String user_employee_id = object.getString("user_employee_id");

                                String[] WG = WelgerGrinder.split(",");
                                for(String name : WG){
                                    if(user_id.equals(name))
                                    {
                                        wokerList.add(user_id);
                                        wokerListvalue.add(user_full_name+" - "+user_employee_id);

                                        Log.d("okccc",user_full_name);
                                    }
                                    //System.out.println(name);

                                }
                                wokerValue[i]=user_full_name+" - "+user_employee_id;
                                wokerId[i]=user_id;

                            }
                            String stringBuilder= String.join(",", wokerListvalue);
                            String stringbuilder = String.join(",",wokerList);

                            worker_w_r.setText(stringBuilder);
                            setweldergrinder.setText(stringbuilder);

                            Collections.reverse(Arrays.asList(listValue));
                            Collections.reverse(Arrays.asList(listId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue12 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue12.add(request12);













        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_category",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String job_category_status;
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("Invalid user request")){
                                Toast.makeText(Update_JobList_Activity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(Update_JobList_Activity.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String job_category_id = object.getString("job_category_id");
                                    String job_category_name = object.getString("job_category_name");
                                    job_category_status = object.getString("job_category_status");
                                    if(job_category_status.equals("1")) {

                                        jobcategoryList.add(new JobCategorySpinner(job_category_id, job_category_name));
                                        jobcategoryAdapter = new ArrayAdapter<JobCategorySpinner>(Update_JobList_Activity.this,
                                                android.R.layout.simple_spinner_dropdown_item, jobcategoryList);
                                        jobcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue.add(request);

        jobCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_JobList_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(850, 1100);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(jobcategoryAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        jobcategoryAdapter.getFilter().filter(charSequence);
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
                        JobCategorySpinner sp = (JobCategorySpinner) parent.getItemAtPosition(position);
                        jobCategory.setText(sp.job_category_name);
                        setcategoryid.setText(sp.job_category_id);
                        // Toast.makeText(Update_JobList_Activity.this, sp.job_category_id, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
            }
        });



        StringRequest request1 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/stock/stock_type",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            listValue = new String[jsonArray.length()];
                            listId = new String[jsonArray.length()];
                            selectedLanguage = new boolean[listValue.length];

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String emp_type_id = object.getString("stock_type_id");
                                String emp_type_name = object.getString("stock_type_name");

                                listValue[i]=emp_type_name;
                                listId[i]=emp_type_id;

                            }

                            Collections.reverse(Arrays.asList(listValue));
                            Collections.reverse(Arrays.asList(listId));

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue1.add(request1);

        consumbles_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_JobList_Activity.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(listValue, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(listId[i]);
                            langListvalue.add(listValue[i]);

                            Log.d("Nilesh",listId[i]);


                            // Sort array list
                            Collections.sort(langList);

                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(listId[i]);
                            langListvalue.remove(listValue[i]);

                        }
                        Log.d("Nil", String.valueOf(langList));
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
//                        StringBuilder stringBuilder = new StringBuilder();
                        String stringBuilder= String.join(",", langListvalue);
                        String stringbuilder = String.join(",",langList);
                        Log.d("nil",stringBuilder);
                        Log.d("nil",stringbuilder);

                        consumbles_item.setText(stringBuilder);
                        setconsum.setText(stringbuilder);

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
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langListvalue.clear();
                            langList.clear();
                            // clear text view value
                            consumbles_item.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });




        StringRequest request2 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_size",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j = 0;
                            String job_size_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(Update_JobList_Activity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(Update_JobList_Activity.this, LoginActivity.class));
                                finish();
                            }
                            else {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);

                                    String job_size_id = object.getString("job_size_id");
                                    String job_size_name = object.getString("job_size_name");

                                    job_size_status = object.getString("job_size_status");
                                    if (job_size_status.equals(String.valueOf(1))) {
                                        jobsizeList.add(new JobSizeSpinner(job_size_id,job_size_name));
                                        jobsizeAdapter = new ArrayAdapter<>(Update_JobList_Activity.this,
                                                android.R.layout.simple_spinner_dropdown_item,jobsizeList);
                                        jobsizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue2 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue2.add(request2);

        jobsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog = new Dialog(Update_JobList_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(950, 1450);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                listView.setAdapter(jobsizeAdapter);
                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        jobsizeAdapter.getFilter().filter(charSequence);
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

                        JobSizeSpinner sp = (JobSizeSpinner) parent.getItemAtPosition(position);
                        jobsize.setText(sp.job_size_name);
                        setjobsize.setText(sp.job_size_id);
                        //Toast.makeText(Update_JobList_Activity.this, sp.job_size_id, Toast.LENGTH_SHORT).show();
                        // Dismiss dialog
                        dialog.dismiss();


                    }
                });
            }
        });


        StringRequest request3 = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_project_manager",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            String user_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(Update_JobList_Activity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(Update_JobList_Activity.this, LoginActivity.class));
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

                                        //Toast.makeText(Update_JobList_Activity.this, user_id, Toast.LENGTH_SHORT).show();
                                        projectManagerList.add(new ProjectManagerSpinner(user_id,user_full_name));
                                        projectManagerAdapter = new ArrayAdapter<>(Update_JobList_Activity.this,
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
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue3 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue3.add(request3);

        projectmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                // Initialize dialog
                dialog = new Dialog(Update_JobList_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);

                // set custom height and width
                dialog.getWindow().setLayout(750, 1100);

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
                        setmanager.setText(sp.user_id);

                        //Toast.makeText(Update_JobList_Activity.this, sp.user_id, Toast.LENGTH_SHORT).show();
                        // Dismiss dialog
                        dialog.dismiss();


                    }
                });
            }
        });




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

                                wokerValue[i]=user_name;
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
                Toast.makeText(Update_JobList_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue4 = Volley.newRequestQueue(Update_JobList_Activity.this);
        requestQueue4.add(request4);

        worker_w_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_JobList_Activity.this);

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

                        worker_w_r.setText(stringBuilder);
                        setweldergrinder.setText(stringbuilder);

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
                            worker_w_r.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });


        arrayList1=new ArrayList<>();
        arrayList1.add("In progress");
        arrayList1.add("Completed");

        status_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(Update_JobList_Activity.this);

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

                ArrayAdapter<String> adapter=new ArrayAdapter<>(Update_JobList_Activity.this, android.R.layout.simple_list_item_1,arrayList1);
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
                        status_check.setText(adapter.getItem(i));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });


    }

}