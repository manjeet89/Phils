package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Adapter.UserAdapterClass;
import com.example.phils.ResponseModels.ResponseModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity {
    RecyclerView recview;
    SearchView searchView;

    UserAdapterClass userAdapterClass;
    List<ResponseModelUser> data;
    ResponseModelUser responseModelUser;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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

        linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();
        userAdapterClass = new UserAdapterClass(this,data);
        recview.setAdapter(userAdapterClass);

        fatchdata();

    }

    private void fileList(String newText) {

        List<ResponseModelUser> modelStockCategories = new ArrayList<>();
        for(ResponseModelUser responseModelUser : data)
        {
            if(responseModelUser.getUser_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            ||responseModelUser.getEmployee_type().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
            ||responseModelUser.getUser_full_name().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                modelStockCategories.add(responseModelUser);
            }
        }

        if (modelStockCategories.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            userAdapterClass.setFilteredList(modelStockCategories);
        }
    }

    private void fatchdata() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://investment-wizards.com/manjeet/Phils_Stock/tbl_user.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String user_status;
                            String employee_type;
                            int stat = 0;
                            int j=0;
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
                                    String user_id = String.valueOf(j);
                                    String user_full_name = object.getString("user_full_name");
                                    String emp_type_name = object.getString("emp_type_name");
                                    String user_name = object.getString("user_name");
                                    String user_phone_number = object.getString("user_phone_number");
                                    String user_otp = object.getString("user_otp");
                                     employee_type = object.getString("employee_type");
                                    if(employee_type.equals(String.valueOf(0)))
                                    {
                                        employee_type = "Temporary";
                                    }
                                    else
                                    {
                                        employee_type = "Permanent";
                                    }
                                    user_status = object.getString("user_status");

                                    if(user_status.equals(String.valueOf(0)))
                                    {
                                        user_status = "Disable";
                                    }
                                    else
                                    {
                                        user_status = "Enable";
                                    }

                                    responseModelUser = new ResponseModelUser(user_id,user_full_name,emp_type_name,user_name,user_phone_number,user_otp,employee_type,user_status);
                                    data.add(responseModelUser);
                                    userAdapterClass.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}