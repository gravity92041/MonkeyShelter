package com.example.androidspringtestapp.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl, TokenManager tokenManager){
        if (retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(tokenManager))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
