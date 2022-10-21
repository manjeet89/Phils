package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button submitlogin;
    CoordinatorLayout layout;
    ProgressDialog progressDialog;
    TextView settoken;


    public boolean isRememberUserLogin = true;
    private AppConfig appConfig;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appConfig = new AppConfig(this);
        if(appConfig.isUserLogin())
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submitlogin = findViewById(R.id.submitlogin);
        layout = findViewById(R.id.layoutchal);

        settoken = findViewById(R.id.settoken);

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



        String username1 = username.getText().toString().trim();
        String password1 = password.getText().toString().trim();


        if (TextUtils.isEmpty(username1)) {
            username.setError("Enter User Name");
            Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password1)) {
            password.setError("Enter Password");

            Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else {

            progressDialog.setMessage("Loading... Please Wait!");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/login",
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


                                if (code.equals("200")) {

                                    if (message.equals("Please verify OTP")) {

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
                                        String location_name = jsonObject1.getString("location_name");

                                        String access_module = jsonObject.getString("access_module");

                                        Intent intent = new Intent(LoginActivity.this, TwoStepVerification.class);
                                        intent.putExtra("user_id", user_id);
                                        intent.putExtra("user_full_name", user_full_name);
                                        intent.putExtra("user_email_id", user_email_id);
                                        intent.putExtra("user_employee_type", user_employee_type);
                                        intent.putExtra("employee_type", employee_type);
                                        intent.putExtra("emp_type_name", emp_type_name);
                                        intent.putExtra("emp_type_id", emp_type_id);
                                        intent.putExtra("project_location_id", project_location_id);
                                        intent.putExtra("location_name", location_name);
                                        intent.putExtra("access_module", access_module);


                                        startActivity(intent);
                                        finish();
                                    } else {

                                        String user_token = jsonObject.getString("user_token").trim();
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
                                        String location_name = jsonObject1.getString("location_name");

                                        String access_module = jsonObject.getString("access_module");

//                                    String text = access_module.toString().replace("[", "").replace("]", "");
//                                    String withoutQuotes_line1 = text.replace("\"", "");
//                                   // Log.d("mekya",withoutQuotes_line1);
//                                    String [] items = withoutQuotes_line1.split("\\s*,\\s*");
//
//                                    for (int i =0;i<items.length;i++)
//                                    {
//                                        if(items[i].equals("stock_low"))
//                                            Log.d("mekya",items[i]);
//
//                                    }


                                        //Log.d("tokennnn",user_token +"/"+user_id+"/"+project_location_id+"/"+user_employee_type+"/"+emp_type_name);

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
                                            appConfig.SaveLocation(location_name);
                                            appConfig.SaveLocationId(project_location_id);
                                            appConfig.Saveaccess_module(access_module);
                                            appConfig.SaveRequisition("true");


                                        }

                                        FirebaseTokenGenerate(user_id, user_email_id);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user_email_id",user_email_id);
                                    intent.putExtra("user_id",user_id);
//                                    intent.putExtra("access_module",access_module);
                                    startActivity(intent);
                                        finish();
                                    }

                                } else {
                                    displayUserInformation(message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {


                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_name", username1);
                    params.put("user_password", password1);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }
        private void FirebaseTokenGenerate(String userId,String UserEmail) {


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d( "Fetching", String.valueOf(task.getException()));
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("firebasetoken",token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();


                        StringRequest request = new StringRequest(Request.Method.POST, "https://erp.philsengg.com/api/login/update_firebase_user_token",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            progressDialog.dismiss();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
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
                                params.put("user_id",userId);
                                params.put("user_email_id",UserEmail);
                                params.put("firebase_user_token",token);

                                return  params;
                            }


                        };
                        RequestQueue  requestQueue = Volley.newRequestQueue(LoginActivity.this);
                        requestQueue.add(request);
                    }
                });
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