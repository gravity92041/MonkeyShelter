package com.example.androidspringtestapp.api;

import com.example.androidspringtestapp.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersApi {
    @GET("admin/showUsers")
    Call<List<Person>> getUsers();
}
