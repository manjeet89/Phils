package com.example.phils.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class TwoStepVerification extends AppCompatActivity {

    Button submitTwostep;
    EditText first,second,third,fourth;
    RequestQueue requestQueue;

    public boolean isRememberUserLogin = true;
    private AppConfig appConfig;
    ProgressDialog progressDialog;
    CoordinatorLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_step_verification);
        requestQueue = Volley.newRequestQueue(this);
        appConfig = new AppConfig(this);


        first = findViewById(R.id.firstnumber);
        second = findViewById(R.id.secondnumber);
        third = findViewById(R.id.thirednumber);
        fourth = findViewById(R.id.fourthnumber);
        layout = findViewById(R.id.layoutchal);
        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(first.getText().toString().length()==1){
                    second.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(second.getText().toString().length()==1){
                    third.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(third.getText().toString().length()==1){
                    fourth.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(fourth.getText().toString().length()==1){
                    CheckTwoStapVerification();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitTwostep = findViewById(R.id.submitTwostep);



        submitTwostep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckTwoStapVerification();
//              String number =   first.getText().toString()+second.getText().toString()+third.getText().toString()+fourth.getText().toString();
//                Toast.makeText(TwoStepVerification.this, number, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void CheckTwoStapVerification() {
        progressDialog = new ProgressDialog(TwoStepVerification.this);
        progressDialog.setMessage("Loading... Please Wait!");
        progressDialog.setIcon(R.drawable.ic_baseline_autorenew_24);
        progressDialog.show();


        String userId = getIntent().getStringExtra("user_id");
        String user_full_name = getIntent().getStringExtra("user_full_name");
        String user_email_id = getIntent().getStringExtra("user_email_id");
        String user_employee_type = getIntent().getStringExtra("user_employee_type");
        String employee_type = getIntent().getStringExtra("employee_type");
        String emp_type_name = getIntent().getStringExtra("emp_type_name");
        String emp_type_id = getIntent().getStringExtra("emp_type_id");
        String project_location_id = getIntent().getStringExtra("project_location_id");
        String location_name = getIntent().getStringExtra("location_name");
        String access_module = getIntent().getStringExtra("access_module");

        String number =   first.getText().toString()+second.getText().toString()+third.getText().toString()+fourth.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/login/verification",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            String status = jsonObject.getString("status");

                            if(code.equals("200")){
                                String user_token = jsonObject.getString("user_token");

                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);

                                String user_name = jsonObject1.getString("user_name");
                                String userId = getIntent().getStringExtra("user_id");
                                String user_email_id = getIntent().getStringExtra("user_email_id");
                                String user_employee_type = getIntent().getStringExtra("user_employee_type");

//                                String employee_type = getIntent().getStringExtra("employee_type");
//                                String emp_type_name = getIntent().getStringExtra("emp_type_name");
//                                String emp_type_id = getIntent().getStringExtra("emp_type_id");
//                                String project_location_id = jsonObject1.getString("project_location_id");
//                                String location_name = jsonObject1.getString("location_name");
//                                String user_full_name = getIntent().getStringExtra("user_full_name");

                                if (isRememberUserLogin) {
                                        appConfig.updateUserLoginStatus(true);
                                        appConfig.Saveuser_name(user_name);
                                        appConfig.Saveuser_id(userId);
                                        appConfig.Saveuser_email_id(user_email_id);
                                        appConfig.Saveuser_employee_type(user_employee_type);
                                        appConfig.Saveemployee_type(employee_type);
                                        appConfig.Saveemp_type_name(emp_type_name);
                                        appConfig.Saveemp_type_id(emp_type_id);
                                        appConfig.Saveuser_token(user_token);
                                        appConfig.SaveLocation(location_name);
                                        appConfig.SaveLocationId(project_location_id);
                                        appConfig.Saveuser_full_name(user_full_name);
                                        appConfig.Saveaccess_module(access_module);

                                        FirebaseTokenGenerate(userId,user_email_id);


//                                    Intent intent = new Intent(TwoStepVerification.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
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
                Toast.makeText(TwoStepVerification.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("user_id",userId);
                params.put("user_email_id",user_email_id);
                params.put("user_otp",number);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TwoStepVerification.this);
        requestQueue.add(request);
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


                        StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/login/update_firebase_user_token",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(TwoStepVerification.this, "Login Successfully", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(TwoStepVerification.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                        RequestQueue  requestQueue = Volley.newRequestQueue(TwoStepVerification.this);
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