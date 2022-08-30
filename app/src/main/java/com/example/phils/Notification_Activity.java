package com.example.phils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.phils.Adapter.JobSizeAdapterClass;
import com.example.phils.ResponseModels.ResponseModelJobSize;
import com.example.phils.Shareprefered.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Notification_Activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recview;
    SearchView searchView;
    Button btn;
    NotificationAdapterClass notificationAdapterClass;
    List<ResponseModelNotification> data;
    ResponseModelNotification responseModelNotification;
    LinearLayoutManager linearLayoutManager;

    AppConfig appConfig;
    TextView location_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

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


        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        notificationAdapterClass = new NotificationAdapterClass(this,data);
        recview.setAdapter(notificationAdapterClass);

        fatchdata();
    }

    private void fileList(String newText) {

        List<ResponseModelNotification> modelStockCategories = new ArrayList<>();
        for(ResponseModelNotification responseModelNotification : data)
        {
            if(responseModelNotification.getNoti_message().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelNotification);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            notificationAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        progressDialog = new ProgressDialog(Notification_Activity.this);
        progressDialog.setTitle("Notification");
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();
        appConfig = new AppConfig(this);
        String user_id = appConfig.getIdOfUser();

        StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_notification.php",
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
                                    String noti_user_id = object.getString("noti_user_id");
                                    String url_created = object.getString("url_created");
                                    String noti_message = object.getString("noti_message");


                                    status = object.getString("noti_status");
                                    if(status.equals(String.valueOf(0)))
                                    {
                                        status = "Unread";
                                    }
                                    else
                                    {
                                        status = "Read";
                                    }

                                    responseModelNotification = new ResponseModelNotification(sn,noti_user_id,url_created,noti_message,status);
                                    data.add(responseModelNotification);
                                    notificationAdapterClass.notifyDataSetChanged();
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
                Toast.makeText(Notification_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("noti_user_id",user_id);
                return params;
            }
        }

                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}