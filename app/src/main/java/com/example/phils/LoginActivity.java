package com.example.phils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Admin.Add_Job_Category_Activity;
import com.example.phils.Admin.MainActivity;
import com.example.phils.ResponseModels.ResponseModelLogin;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button submitlogin;
    CoordinatorLayout layout;
    ProgressDialog progressDialog;


    public boolean isRememberUserLogin = true;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appConfig = new AppConfig(this);
        if(appConfig.isUserLogin())
        {
//            String name = appConfig.getNameOfUser();
//            String id = appConfig.getIdOfUser();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.putExtra("name",name);
//            intent.putExtra("id",id);
            startActivity(intent);
            finish();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submitlogin = findViewById(R.id.submitlogin);
        layout = findViewById(R.id.layoutchal);


        submitlogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        userLogin();
                    }
                });
    }

    private void userLogin() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();
        String username1 = username.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/login",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            String status = jsonObject.getString("status");
                            //Toast.makeText(LoginActivity.this, code, Toast.LENGTH_SHORT).show();


                            if(code.equals("200")){

                                if(message.equals("Please verify OTP")){

                                    String data = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data);

                                    String user_id = jsonObject1.getString("user_id");
                                    String user_full_name = jsonObject1.getString("user_full_name");
                                    String user_email_id = jsonObject1.getString("user_email_id");
                                    String user_employee_type = jsonObject1.getString("user_employee_type");
                                    String employee_type = jsonObject1.getString("employee_type");
                                    String emp_type_name = jsonObject1.getString("emp_type_name");
                                    String emp_type_id = jsonObject1.getString("emp_type_id");
                                    String project_location_id = jsonObject1.getString("project_location_id");


                                    Intent intent = new Intent(LoginActivity.this, TwoStepVerification.class);
                                    intent.putExtra("user_id",user_id);
                                    intent.putExtra("user_full_name",user_full_name);
                                    intent.putExtra("user_email_id",user_email_id);
                                    intent.putExtra("user_employee_type",user_employee_type);
                                    intent.putExtra("employee_type",employee_type);
                                    intent.putExtra("emp_type_name",emp_type_name);
                                    intent.putExtra("emp_type_id",emp_type_id);
                                    intent.putExtra("project_location_id",project_location_id);


                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    String user_token = jsonObject.getString("user_token");
                                    // Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    String data = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data);

                                    String user_id = jsonObject1.getString("user_id");
                                    String user_full_name = jsonObject1.getString("user_full_name");
                                    String user_email_id = jsonObject1.getString("user_email_id");
                                    String user_employee_type = jsonObject1.getString("user_employee_type");
                                    String employee_type = jsonObject1.getString("employee_type");
                                    String emp_type_name = jsonObject1.getString("emp_type_name");
                                    String emp_type_id = jsonObject1.getString("emp_type_id");
                                    String project_location_id = jsonObject1.getString("project_location_id");

                                    if (isRememberUserLogin) {
                                        appConfig.updateUserLoginStatus(true);
                                        appConfig.Saveuser_id(user_id);
                                        appConfig.Saveuser_full_name(user_full_name);
                                        appConfig.Saveuser_email_id(user_email_id);
                                        appConfig.Saveuser_employee_type(user_employee_type);
                                        appConfig.Saveemployee_type(employee_type);
                                        appConfig.Saveemp_type_name(emp_type_name);
                                        appConfig.Saveemp_type_id(emp_type_id);
                                        appConfig.Saveuser_token(user_token);
                                        appConfig.SaveLocation(project_location_id);


                                    }

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            else
                            {
                                displayUserInformation(message);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {


            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("user_name",username1);
                params.put("user_password",password1);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }

//    private void userLogin() {
//        progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setMessage("Loading... Please Wait!");
//        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
//        progressDialog.show();
//
//        String username1 = username.getText().toString().trim();
//        String password1 = password.getText().toString().trim();
//        Call<ResponseModelLogin> call = ApiController.getInstance().getapi().userLogin(username1,password1);
//        call.enqueue(new Callback<ResponseModelLogin>() {
//            @Override
//            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {
//                if(response.code()==200)
//                {
//                    if(response.body().getStatus().equals("ok"))
//                    {
//                       if(response.body().getResultCode()==1)
//                       {
//                           String id = response.body().getUser_id();
//                           String name = response.body().getName();
//                           String user = response.body().getUsername();


//                           if(user.equals("admin")) {
//
//
//                               if(isRememberUserLogin)
//                               {
//                                   appConfig.updateUserLoginStatus(true);
//                                   appConfig.saveNameOfUser(name);
//                                   appConfig.saveUserName(user);
//                                   appConfig.saveIdOfUser(id);
//                               }
//
//                               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                               intent.putExtra("name", name);
//                               intent.putExtra("id", id);
//                               startActivity(intent);
//                               finish();
//                           }
//                           else {


//                               FirebaseMessaging.getInstance().getToken()
//                                       .addOnCompleteListener(new OnCompleteListener<String>() {
//                                           @Override
//                                           public void onComplete(@NonNull Task<String> task) {
//                                               if (task.isSuccessful()) {
//                                                   String usertoken = Objects.requireNonNull(task.getResult());
//                                                   Log.d("tooo", "token: " + usertoken);
//
//
//                                                   StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_token.php",
//                                                           new com.android.volley.Response.Listener<String>() {
//                                                               @Override
//                                                               public void onResponse(String response) {
//                                                                //   Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
//                                                               }
//                                                           }, new com.android.volley.Response.ErrorListener() {
//                                                       @Override
//                                                       public void onErrorResponse(VolleyError error) {
//                                                           Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                                       }
//                                                   }) {
//                                                       @Nullable
//                                                       @Override
//                                                       protected Map<String, String> getParams() throws AuthFailureError {
//                                                           Map<String, String> params = new HashMap<String, String>();
//                                                           params.put("user_id", id);
//                                                           params.put("user_token", usertoken);
//                                                           return params;
//                                                       }
//                                                   };
//                                                   RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
//                                                   requestQueue.add(request);
//
//                                                   Random random = new Random();
//                                                   String id1 = String.format("%04d", random.nextInt(10000));
//
//
//                                                   StringRequest request1 = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_otp.php",
//                                                           new com.android.volley.Response.Listener<String>() {
//                                                               @Override
//                                                               public void onResponse(String response) {
//                                                                //   Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
//                                                               }
//                                                           }, new com.android.volley.Response.ErrorListener() {
//                                                       @Override
//                                                       public void onErrorResponse(VolleyError error) {
//                                                           Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                                       }
//                                                   }) {
//                                                       @Nullable
//                                                       @Override
//                                                       protected Map<String, String> getParams() throws AuthFailureError {
//                                                           Map<String, String> params = new HashMap<String, String>();
//                                                           params.put("user_id", id);
//                                                           params.put("user_otp", id1);
//                                                           return params;
//                                                       }
//                                                   };
//                                                   RequestQueue requestQueue1 = Volley.newRequestQueue(LoginActivity.this);
//                                                   requestQueue1.add(request1);
//
//
//                                                   Intent intent = new Intent(LoginActivity.this, TwoStepVerification.class);
//                                                   // intent.putExtra("name",name);
//                                                   intent.putExtra("id", id);
//                                                   startActivity(intent);
//
//                                               }
//                                           }
//                                       });
//                               finish();
                         //  }
//                           progressDialog.dismiss();
//                       }
//                       else
//                       {
//                            displayUserInformation("Something Went wrong");
//                       }
//                    }
//                    else
//                    {
//                        displayUserInformation("Invalid Username or password");
//
//                       // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else
//                {
//                    displayUserInformation("Something  wrong");
//
//                    // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {
//
//            }
//        });

    //}
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