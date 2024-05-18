package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.model.Monkey;
import com.example.androidspringtestapp.util.MonkeyAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MonkeyAdapter monkeyAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username").toString();
        String role = intent.getStringExtra("role");
        Log.i("role", role);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MonkeyApi monkeyApi = retrofit.create(MonkeyApi.class);
        Call<List<Monkey>> call = monkeyApi.getMonkeys();
        call.enqueue(new Callback<List<Monkey>>() {
            @Override
            public void onResponse(Call<List<Monkey>> call, Response<List<Monkey>> response) {
                if (!response.isSuccessful()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Ошибка из он респонс: ")
                            .setPositiveButton("OK", null)
                            .show();
                }
                List<Monkey> monkeys = response.body();
                monkeyAdapter = new MonkeyAdapter(MainActivity.this, monkeys);
                recyclerView.setAdapter(monkeyAdapter);

            }

            @Override
            public void onFailure(Call<List<Monkey>> call, Throwable t) {
                Log.e("Api error", t.getMessage(), t);
            }
        });
    }
}