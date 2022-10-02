package com.example.phils.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText currentpass,newpass,confirmnewpass;
    TextView error;
    Button update;
    AppConfig appConfig;
    RequestQueue requestQueue;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        requestQueue = Volley.newRequestQueue(this);

        currentpass = findViewById(R.id.currentpass);
        newpass = findViewById(R.id.newpass);
        confirmnewpass = findViewById(R.id.confirmnewpass);
         error = findViewById(R.id.TextView_PwdProblem);
         update = findViewById(R.id.changePassword);
         update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ChangePassword();
             }
         });

         confirmnewpass.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 String strPass1 = newpass.getText().toString();
                 String strPass2 = confirmnewpass.getText().toString();
                 if (strPass1.equals(strPass2)) {
                     error.setText("Password Matched");
                 } else {
                     error.setText("Password Not Matached");
                 }
             }
         });
    }

    private void ChangePassword() {
        String oldpassword = currentpass.getText().toString();
        String newpassword = newpass.getText().toString();
        String confirmpassword = confirmnewpass.getText().toString();
        String errorCheck = error.getText().toString();

        appConfig = new AppConfig(this);
        String token = appConfig.getuser_token();
        String userId = appConfig.getuser_id();
        String location  = appConfig.getLocationId();
        String user_employee_type = appConfig.getuser_employee_type();
        //String userId = appConfig.getIdOfUser();

        if(TextUtils.isEmpty(oldpassword))
        {
            currentpass.setError("Please Enter Old Password");
            Toast.makeText(ChangePasswordActivity.this, "Please Enter Old Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(newpassword))
        {
            newpass.setError("Please Enter New Password");
            Toast.makeText(ChangePasswordActivity.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(confirmpassword))
        {
            confirmnewpass.setError("Please Enter Confirm Password");
            Toast.makeText(ChangePasswordActivity.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(errorCheck.equals("Password Matched"))
            {

                StringRequest request = new StringRequest(Request.Method.POST, "https://mployis.com/staging/api/user/changepassword",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("message");

                                    if(message.equals("User password changed successfully")) {
                                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));

                                    }
                                    else if(message.equals("please enter valid password")){
                                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePasswordActivity.this, "Confirm Password is doesn't Match", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(Add_Stock_Category_Activity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("user_token",token);
                        headers.put("user_id", userId);
                        headers.put("project_location_id", location);
                        headers.put("user_employee_type", user_employee_type);

                        return headers;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("current_password",oldpassword);
                        params.put("new_password",newpassword);
                        params.put("confirm_password",confirmpassword);

                        return  params;
                    }


                };
                RequestQueue  requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
                requestQueue.add(request);

//                StringRequest request  = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/ChangePasswordUser.php",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ChangePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }){
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                      //  params.put("user_id", userId);
//                        params.put("oldpass", oldpassword);
//                        params.put("newpass", newpassword);
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
//                requestQueue.add(request);
            }
            else
            {
                Toast.makeText(this, "Confirm Password is Not Match", Toast.LENGTH_SHORT).show();
            }


        }

    }
}