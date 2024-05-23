package com.example.androidspringtestapp.api;

import com.example.androidspringtestapp.model.LoginRequest;
import com.example.androidspringtestapp.model.LoginResponse;
import com.example.androidspringtestapp.model.Monkey;
import com.example.androidspringtestapp.model.RegistrationRequest;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MonkeyApi {
    @GET("monkey")
    Call<List<Monkey>> getMonkeys();
    @POST("auth/registration")
    Call<Void> registerUser(@Body RegistrationRequest registrationRequest);

    @POST("auth/login")
    Call<Map<String,String>> signIn(@Body LoginRequest loginRequest);

    @GET("monkey/{id}")
    Call<Monkey> getMonkey(@Path("id")int id);
}
