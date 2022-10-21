package com.example.phils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    private static final String url ="https://erp.philsengg.com/api/";
            //"https://investment-wizards.com/manjeet/Phils_Stock/";
    private static ApiController apiController;
    private static Retrofit retrofit;

    ApiController()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance()
    {
        if(apiController==null)
            apiController = new ApiController();

        return apiController;
    }
    ApiSet getapi()
    {
        return  retrofit.create(ApiSet.class);
    }

}
