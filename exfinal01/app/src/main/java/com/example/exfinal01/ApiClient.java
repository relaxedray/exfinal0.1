package com.example.exfinal01;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Inter inter = retrofit.create(Inter.class);
        return retrofit;
    }
    public static Inter getUserService(){
        Inter inter = getRetrofit().create(Inter.class);
        return inter;
    }


}
