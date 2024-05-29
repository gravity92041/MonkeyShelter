package com.example.androidspringtestapp.activities;

import android.annotation.SuppressLint;
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
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;

import java.util.ArrayList;
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
    private TokenManager tokenManager;
    List<Monkey> monkeys;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentFrom = getIntent();
        String username = intentFrom.getStringExtra("username");
        String role = intentFrom.getStringExtra("role");
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.monkeySearchView);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.monkeyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);
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
                monkeys = response.body();
                monkeyAdapter = new MonkeyAdapter(MainActivity.this, monkeys);
                recyclerView.setAdapter(monkeyAdapter);
                monkeyAdapter.setOnMonkeyClickListener(new MonkeyAdapter.OnMonkeyClickListener() {
                    @Override
                    public void onMonkeyClick(int monkeyId) {
                        Intent intent = new Intent(MainActivity.this, MonkeyDetailActivity.class);
                        intent.putExtra("monkeyId",String.valueOf(monkeyId));
                        intent.putExtra("username",username);
                        intent.putExtra("role",role);
                        Log.i("FWEFHJSAHFOESOAFSERHOOOOOOOOSG", String.valueOf(monkeyId));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Monkey>> call, Throwable t) {
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
        List<Monkey> filteredList = new ArrayList<>();
        for (Monkey m:monkeys){
            if (m.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(m);
            }
        }
        monkeyAdapter.setFilteredList(filteredList);
    }
}