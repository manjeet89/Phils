package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.example.phils.ResponseModels.ResponseModelUserProfile;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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