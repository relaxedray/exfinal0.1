package com.example.exfinal01;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;
import  retrofit2.http.Query;
import okhttp3.RequestBody;

public interface Inter {

    @POST("login")
    Call<LoginResponse> userlogin(@Body LoginRequest loginrequest);



}
