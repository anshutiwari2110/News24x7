package com.example.news24x7;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface API_Interface {

    @GET("/v2/top-headlines")
    Call<Result> getNews(@QueryMap Map<String, Object> options);

}
