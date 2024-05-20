package com.example.androidspringtestapp.activities;

import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.UsersApi;
import com.example.androidspringtestapp.model.Person;
import com.example.androidspringtestapp.util.PeopleAdapter;
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PeopleAdapter peopleAdapter;
    private SearchView searchView;
    private TokenManager tokenManager;
    List<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);
        recyclerView = findViewById(R.id.usersList);
        searchView = findViewById(R.id.usersSearchView);
        searchView.clearFocus();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);
        UsersApi usersApi = retrofit.create(UsersApi.class);
        Call<List<Person>> call = usersApi.getUsers();
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (!response.isSuccessful()) {
                    new AlertDialog.Builder(UsersViewActivity.this)
                            .setMessage("Ошибка из он респонс: ")
                            .setPositiveButton("OK", null)
                            .show();
                }
                people = response.body();
                peopleAdapter = new PeopleAdapter(UsersViewActivity.this,people);
                recyclerView.setAdapter(peopleAdapter);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("Api error", t.getMessage(), t);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }
    public void filterList(String newText){
        List<Person> filteredList = new ArrayList<>();
        for (Person p:people){
            if (p.getUsername().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(p);
            }
        }
        peopleAdapter.setFilteredList(filteredList);
    }
}