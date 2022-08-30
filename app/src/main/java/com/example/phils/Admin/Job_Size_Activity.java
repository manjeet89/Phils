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
import com.example.phils.Adapter.JobSizeAdapterClass;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobSize;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Job_Size_Activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;
    JobSizeAdapterClass jobSizeAdapterClass;
    List<ResponseModelJobSize> data;
    ResponseModelJobSize responseModelJobSize;
    LinearLayoutManager linearLayoutManager;

    TextView location_save;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_size);

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
        btn = findViewById(R.id.add_job_size);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Add_Job_Size_Activity.class));
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        jobSizeAdapterClass = new JobSizeAdapterClass(this,data);
        recview.setAdapter(jobSizeAdapterClass);

        fatchdata();
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
        progressDialog.setTitle("Job Size");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_job_size.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String status;
                            int stat = 0;
                            int j=0;
                            String cat_group;
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(success.equals("1"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    j++;
                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    String sn = object.getString("stock_category_id");
                                    String sn = String.valueOf(j);
                                    String category = object.getString("job_size_name");
                                    status = object.getString("job_size_status");
                                    if(status.equals(String.valueOf(0)))
                                    {
                                        status = "Disable";
                                    }
                                    else
                                    {
                                        status = "Enable";
                                    }

                                    responseModelJobSize = new ResponseModelJobSize(sn,category,status);
                                    data.add(responseModelJobSize);
                                    jobSizeAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(Job_Size_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}