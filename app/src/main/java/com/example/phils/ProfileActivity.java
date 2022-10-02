package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Admin.LoginActivity;
import com.example.phils.ProfileActivity;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.ResponseModels.ResponseModelUserProfile;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {

    TextView adminName,adminPost,adminMobile,adminEmail,adminEmp,h1;
    AppConfig appConfig;
    ProgressDialog progressDialog;
    DrawerLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        adminName = findViewById(R.id.h1);
        adminPost = findViewById(R.id.adminPost);
        adminMobile = findViewById(R.id.adminMobile);
        adminEmail = findViewById(R.id.adminEmail);
        adminEmp = findViewById(R.id.adminEmp);
        layout = findViewById(R.id.drawer_layout);

        fatchdata();

    }

    private void fatchdata() {

        appConfig = new AppConfig(this);
       // String ID = appConfig.getIdOfUser();

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.show();

        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location = appConfig.getLocationId();

        //Toast.makeText(this, token+"/"+userId, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/profile",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            int j=0;


                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Invalid user request")){
                                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                                appConfig.updateUserLoginStatus(false);
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                    String data = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data);
                                    String Name = jsonObject1.getString("user_full_name");
                                    String Post = jsonObject1.getString("emp_type_name");
                                    String phone = jsonObject1.getString("user_phone_number");
                                    String email = jsonObject1.getString("user_email_id");
                                    String Emp = jsonObject1.getString("user_employee_id");

                                    adminName.setText(Name);
                                    adminPost.setText(Post);
                                    adminMobile.setText(phone);
                                    adminEmail.setText(email);
                                    adminEmp.setText(Emp);

                                    progressDialog.dismiss();


                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(request);

//        //Call<ResponseModelUserProfile> call = ApiController.getInstance().getapi().AdminProfile(ID);
//        call.enqueue(new Callback<ResponseModelUserProfile>() {
//            @Override
//            public void onResponse(Call<ResponseModelUserProfile> call, Response<ResponseModelUserProfile> response) {
//                if(response.code()==200)
//                {
//                    if(response.body().getStatus().equals("ok"))
//                    {
//                        if(response.body().getResultCode()==1)
//                        {
//                            String name  = response.body().getUser_full_name();
//                            String post = response.body().getUser_name();
//                            String phone = response.body().getUser_phone_number();
//                            String email = response.body().getUser_email_id();
//                            String Emp = response.body().getUser_employee_id();
//
//                            String n = name;
//                            adminName.setText(n);
//                            adminPost.setText(post);
//                            adminMobile.setText(phone);
//                            adminEmail.setText(email);
//                            adminEmp.setText(Emp);
//                            //Toast.makeText(ProfileActivity.this, name+"Success", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//
//
//                        }
//                        else
//                        {
//                            displayUserInformation("Something Went wrong");
//                        }
//                    }
//                    else
//                    {
//                        displayUserInformation("Invalid Username or password");
//
//                        // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else
//                {
//                    displayUserInformation("Something  wrong");
//
//                    // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModelUserProfile> call, Throwable t) {
//
//            }
//        });

    }
    private void displayUserInformation(String message)
    {
        // Snackbar.make(layout, message,Snackbar.LENGTH_SHORT).show();
        Snackbar snackbar
                = Snackbar
                .make(
                        layout,
                        message,
                        Snackbar.LENGTH_LONG);


        snackbar.show();
        progressDialog.dismiss();
    }
}