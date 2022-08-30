package com.example.phils;

import com.example.phils.ResponseModels.ResponseModelLogin;
import com.example.phils.ResponseModels.ResponseModelUser;
import com.example.phils.ResponseModels.ResponseModelUserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiSet {
    @GET("tbl_user.php")
    Call<List<ResponseModelUser>> getdata();

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModelLogin> userLogin(@Field("user_name")String user_name , @Field("user_password")String user_password);

    @FormUrlEncoded
    @POST("userProfile.php")
    Call<ResponseModelUserProfile> AdminProfile(@Field("user_id")String user_id);

}
