package com.example.phils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    RecyclerView recview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();


    }

    private void processdata() {
        Call<List<ResponseModelUser>> call = ApiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<ResponseModelUser>>() {
            @Override
            public void onResponse(Call<List<ResponseModelUser>> call, Response<List<ResponseModelUser>> response) {
                List<ResponseModelUser> data = response.body();
                UserAdapterClass userAdapterClass = new UserAdapterClass(data);
                recview.setAdapter(userAdapterClass);
            }

            @Override
            public void onFailure(Call<List<ResponseModelUser>> call, Throwable t) {
                Toast.makeText(UserActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}