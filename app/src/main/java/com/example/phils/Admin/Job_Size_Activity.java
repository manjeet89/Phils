package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.JobSizeAdapterClass;
import com.example.phils.Adapter.StockMakeAdapterClass;
import com.example.phils.Demo;
import com.example.phils.LoginActivity;
import com.example.phils.ProfileActivity;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobCategory;
import com.example.phils.ResponseModels.ResponseModelJobSize;
import com.example.phils.Shareprefered.AppConfig;
import com.example.phils.Update_JobSize_Activity;
import com.example.phils.UserActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Job_Size_Activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;
    ImageView img,profile;

    JobSizeAdapterClass jobSizeAdapterClass;
    List<ResponseModelJobSize> data;
    ResponseModelJobSize responseModelJobSize;
    LinearLayoutManager linearLayoutManager;

    TextView location_save;
    AppConfig appConfig;
    private JobSizeAdapterClass.RecycleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_size);

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.ghar:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        break;

                    case R.id.category_stock:
                        startActivity(new Intent(getApplicationContext(),StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(),StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
                        break;

                    case R.id.make_stock:
                        startActivity(new Intent(getApplicationContext(),StockMakeActivity.class));
                        break;

                    case R.id.umo_stock:
                        startActivity(new Intent(getApplicationContext(),StockUomActivity.class));
                        break;

                    case R.id.list_stock:
                        startActivity(new Intent(getApplicationContext(),StockListActivity.class));
                        break;

                    case R.id.category_job:
                        startActivity(new Intent(getApplicationContext(), Job_Category_Activity.class));
                        break;

                    case R.id.Size_job:
                        startActivity(new Intent(getApplicationContext(), Job_Size_Activity.class));
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        recview = findViewById(R.id.recview);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(Job_Size_Activity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_profile_dialog);

                // set custom height and width
                dialog.getWindow().setLayout(750,1050);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                String emp_name = appConfig.getemp_type_name();
                String fullName = appConfig.getuser_full_name();

                TextView nameAdmin = dialog.findViewById(R.id.nameAdmin);
                TextView post = dialog.findViewById(R.id.postAdmin);
                nameAdmin.setText(fullName);
                post.setText(emp_name);




                Button logout = dialog.findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(Job_Size_Activity.this,LoginActivity.class));
                        finish();
                    }
                });
                TextView textView = dialog.findViewById(R.id.my_profile);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                });
                TextView ChangePassword = dialog.findViewById(R.id.change_pas);
                ChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                    }
                });

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });
        btn = findViewById(R.id.add_job_size);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Add_Job_Size_Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
            }
        });
        fatchdata();
        recycleClickLister();

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        jobSizeAdapterClass = new JobSizeAdapterClass(listener,data);
        recview.setAdapter(jobSizeAdapterClass);


    }

    private void fileList(String newText) {

        List<ResponseModelJobSize> modelStockCategories = new ArrayList<>();
        for(ResponseModelJobSize responseModelJobSize : data)
        {
            if(responseModelJobSize.getJob_size_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelJobSize);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            jobSizeAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {


        progressDialog = new ProgressDialog(Job_Size_Activity.this);
        progressDialog.setTitle("Job Category");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocation();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_size",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j = 0;
                            String job_size_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.equals("Invalid user request")){
                                Toast.makeText(Job_Size_Activity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(Job_Size_Activity.this, LoginActivity.class));
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
                                if (job_size_status.equals(String.valueOf(0))) {
                                    job_size_status = "Disable";
                                } else {
                                    job_size_status = "Enable";
                                }


                                String job_size_updated_on = object.getString("job_size_updated_on");
                                String job_size_created_on = object.getString("job_size_created_on");


                                responseModelJobSize = new ResponseModelJobSize(sn, job_size_id, job_size_name, job_size_status, job_size_updated_on, job_size_created_on);
                                data.add(responseModelJobSize);
                                jobSizeAdapterClass.notifyDataSetChanged();
                                progressDialog.dismiss();

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
                Toast.makeText(Job_Size_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Job_Size_Activity.this);
        requestQueue.add(request);


//
    }

    private void recycleClickLister() {
        listener = new JobSizeAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String kk = data.get(position).getJob_size_id();
                String name = data.get(position).getJob_size_name();
                String status = data.get(position).getJob_size_status();

                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocation();

                Intent intent = new Intent(getApplicationContext(), Update_JobSize_Activity.class);
                intent.putExtra("id", kk);
                intent.putExtra("sizename", name);
                intent.putExtra("sizestatus", status);

                intent.putExtra("token", token);
                intent.putExtra("userId", userId);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        };
    }
}