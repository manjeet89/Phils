package com.example.phils.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.JobCategoryAdapterClass;
import com.example.phils.Adapter.StockMakeAdapterClass;
import com.example.phils.Demo;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobCategory;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Job_Category_Activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;
    JobCategoryAdapterClass jobCategoryAdapterClass;
    List<ResponseModelJobCategory> data;
    ResponseModelJobCategory responseModelJobCategory;
    LinearLayoutManager linearLayoutManager;

    TextView location_save;
    AppConfig appConfig;

    private JobCategoryAdapterClass.RecycleViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_category);

        appConfig = new AppConfig(this);
        location_save = findViewById(R.id.location_save);
        String location_save1 = appConfig.getLocation();
        location_save.setText(location_save1);

        recview = findViewById(R.id.recview);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();

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
        jobCategoryAdapterClass = new JobCategoryAdapterClass(listener,data);
        recview.setAdapter(jobCategoryAdapterClass);


        btn = findViewById(R.id.add_job_category);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Add_Job_Category_Activity.class));
            }
        });




    }

    private void fileList(String newText) {

        List<ResponseModelJobCategory> modelStockCategories = new ArrayList<>();
        for(ResponseModelJobCategory responseModelJobCategory : data)
        {
            if(responseModelJobCategory.getJob_category_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelJobCategory);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            jobCategoryAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        progressDialog = new ProgressDialog(Job_Category_Activity.this);
        progressDialog.setTitle("Job Category");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_job_category.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String status;
                            int stat = 0;
                            int j = 0;
                            String cat_group;
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String job_category_id = object.getString("job_category_id");
                                    String sn = String.valueOf(j);
                                    String category = object.getString("job_category_name");
                                    status = object.getString("job_category_status");
                                    if (status.equals(String.valueOf(0))) {
                                        status = "Disable";
                                    } else {
                                        status = "Enable";
                                    }

                                    responseModelJobCategory = new ResponseModelJobCategory(sn,job_category_id, category, status);
                                    data.add(responseModelJobCategory);
                                    jobCategoryAdapterClass.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Job_Category_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
        private void recycleClickLister() {
            listener = new JobCategoryAdapterClass.RecycleViewClickListener() {
                @Override
                public void onClick(View v, int position) {
                    String kk = data.get(position).getOb_category_id();
                    Intent intent = new Intent(getApplicationContext(), Demo.class);
                    intent.putExtra("username", kk);
                    startActivity(intent);
                }
            };
        }
    }