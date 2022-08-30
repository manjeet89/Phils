package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.phils.Admin.MainActivity;
import com.example.phils.ResponseModels.ResponseModelLogin;
import com.example.phils.Shareprefered.AppConfig;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String name = appConfig.getNameOfUser();
            String id = appConfig.getIdOfUser();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("id",id);
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
        Call<ResponseModelLogin> call = ApiController.getInstance().getapi().userLogin(username1,password1);
        call.enqueue(new Callback<ResponseModelLogin>() {
            @Override
            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {
                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {
                       if(response.body().getResultCode()==1)
                       {
                           String id = response.body().getUser_id();
                           String name = response.body().getName();
                           String user = response.body().getUsername();

                        if(isRememberUserLogin)
                        {
                            appConfig.updateUserLoginStatus(true);
                            appConfig.saveNameOfUser(name);
                            appConfig.saveUserName(user);
                            appConfig.saveIdOfUser(id);
                        }

                           Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                           intent.putExtra("name",name);
                           intent.putExtra("id",id);
                           startActivity(intent);
                           finish();
//                           progressDialog.dismiss();
                       }
                       else
                       {
                            displayUserInformation("Something Went wrong");
                       }
                    }
                    else
                    {
                        displayUserInformation("Invalid Username or password");

                       // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    displayUserInformation("Something  wrong");

                    // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {

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