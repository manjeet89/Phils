package com.example.phils;

import com.example.phils.ResponseModels.ResponseModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSet {
    @GET("tbl_user.php")
    Call<List<ResponseModelUser>> getdata();

}
