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
import com.example.phils.Adapter.JobListAdapterClass;
import com.example.phils.Add_Job_List_Activity;
import com.example.phils.JobList_Update;
import com.example.phils.JobReplaceUseerActivity;
import com.example.phils.ProfileActivity;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobList;
import com.example.phils.Shareprefered.AppConfig;
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

public class Job_List_Activity extends AppCompatActivity {
    ProgressDialog progressDialog;

    RecyclerView recview;
    SearchView searchView;

    JobListAdapterClass jobListAdapterClass;
    List<ResponseModelJobList> data;
    ResponseModelJobList responseModelJobList;
    LinearLayoutManager linearLayoutManager;
    Button button,add_job;
    ImageView img,profile;

    TextView location_save,replace_user;
    AppConfig appConfig;

    private JobListAdapterClass.RecycleViewClickListener listener;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        button = findViewById(R.id.assing_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), Assign_user_Job_Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
               // startActivity(new Intent(getApplicationContext(), Assign_user_Job_Activity.class));
            }
        });

        add_job = findViewById(R.id.add_job);
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), Add_Job_List_Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();            }
        });

        replace_user = findViewById(R.id.replace_user);
        replace_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), JobReplaceUseerActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("userId",userId);
                intent.putExtra("location",location);

                startActivity(intent);
                finish();
            }
        });



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
                        startActivity(new Intent(getApplicationContext(), StockCategoryActivity.class));
                        break;

                    case R.id.type_stock:
                        startActivity(new Intent(getApplicationContext(), StockTypeActivity.class));
                        break;

                    case R.id.size_stock:
                        startActivity(new Intent(getApplicationContext(), StockSizeActivity.class));
                        break;

                    case R.id.make_stock:
                        startActivity(new Intent(getApplicationContext(), StockMakeActivity.class));
                        break;

                    case R.id.umo_stock:
                        startActivity(new Intent(getApplicationContext(), StockUomActivity.class));
                        break;

                    case R.id.list_stock:
                        startActivity(new Intent(getApplicationContext(), StockListActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                //Toast.makeText(MainActivity.this, "desh", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(Job_List_Activity.this);

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
                        startActivity(new Intent(Job_List_Activity.this, LoginActivity.class));
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
        fatchdata();
        recycleClickLister();

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();
        jobListAdapterClass = new JobListAdapterClass(listener,data);
        recview.setAdapter(jobListAdapterClass);


    }

    private void fileList(String newText) {

        List<ResponseModelJobList> modelStockCategories = new ArrayList<>();
        for(ResponseModelJobList responseModelStockSize : data)
        {
            if(responseModelStockSize.getJob_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            || responseModelStockSize.getJob_number().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelStockSize);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            jobListAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {
        progressDialog = new ProgressDialog(Job_List_Activity.this);
        progressDialog.setTitle("Stock Size");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();



        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/job/job_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;
                            String job_status;

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
//                            Toast.makeText(StockListActivity.this, message, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                            if(message.equals("Invalid user request")){
                                Toast.makeText(Job_List_Activity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(Job_List_Activity.this, LoginActivity.class));
                                finish();
                            }
                            else {


                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String sn = String.valueOf(j);

                                    String job_id = object.getString("job_id");
                                    String job_name = object.getString("job_name");
                                    String job_category_id = object.getString("job_category_id");
                                    String job_number = object.getString("job_number");
                                    String job_size_id = object.getString("job_size_id");
                                    String job_location_id = object.getString("job_location_id");

                                    String consumables_items = object.getString("consumables_items");
                                    String seam_number = object.getString("seam_number");
                                    String job_manager_id = object.getString("job_manager_id");

                                    String job_emp_user = object.getString("job_emp_user");
                                     job_status = object.getString("job_status");
                                    if (job_status.equals(String.valueOf(2))) {
                                        job_status = "Completed";
                                    } else {
                                        job_status = "In Progress";
                                    }
                                    String job_category_name = object.getString("job_category_name");
                                    String job_size_name = object.getString("job_size_name");
                                    String user_full_name = object.getString("user_full_name");



                                    responseModelJobList = new ResponseModelJobList(sn, job_id, job_name, job_category_id, job_number, job_size_id,
                                            job_location_id,consumables_items,seam_number,job_manager_id,job_emp_user,job_status,job_category_name,
                                            job_size_name,user_full_name);
                                    data.add(responseModelJobList);
                                    jobListAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(Job_List_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Job_List_Activity.this);
        requestQueue.add(request);


    }
    private void recycleClickLister() {
        listener = new JobListAdapterClass.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String kk = data.get(position).getJob_id();

                String job_name = data.get(position).getJob_name();
                String job_category_id = data.get(position).getJob_category_id();
                String job_category_name = data.get(position).getJob_category_name();
                String job_size_id = data.get(position).getJob_size_id();
                String job_size_name = data.get(position).getJob_size_name();
                String job_manager_id = data.get(position).getJob_manager_id();
                String user_full_name = data.get(position).getUser_full_name();
                String job_number = data.get(position).getJob_number();
                String consumables_items = data.get(position).getConsumables_items();
                String seam_number = data.get(position).getSeam_number();
                String job_emp_user = data.get(position).getJob_emp_user();
                String job_status = data.get(position).getJob_status();


                String token = appConfig.getuser_token();
                String userId = appConfig.getuser_id();
                String location = appConfig.getLocationId();

                Intent intent = new Intent(getApplicationContext(), Update_JobList_Activity.class);
                intent.putExtra("id", kk);

                intent.putExtra("token", token);
                intent.putExtra("userId", userId);
                intent.putExtra("location", location);


                intent.putExtra("job_name", job_name);
                intent.putExtra("job_category_id", job_category_id);
                intent.putExtra("job_category_name", job_category_name);
                intent.putExtra("job_size_id", job_size_id);
                intent.putExtra("job_size_name", job_size_name);
                intent.putExtra("job_manager_id", job_manager_id);
                intent.putExtra("user_full_name", user_full_name);
                intent.putExtra("job_number", job_number);
                intent.putExtra("consumables_items", consumables_items);
                intent.putExtra("seam_number", seam_number);
                intent.putExtra("job_emp_user", job_emp_user);
                intent.putExtra("job_status", job_status);

                startActivity(intent);
            }
        };
    }
}