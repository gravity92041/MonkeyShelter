package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.model.Monkey;
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonkeyDetailActivity extends AppCompatActivity {
    private TokenManager tokenManager;
    ImageView monkeyDetailImageView;

    TextView monkeyDetailNameTextView,monkeyDetailOwner,monkeyDetailAgeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monkey_detail);
        Intent intentFrom = getIntent();
        String fromIntent = intentFrom.getStringExtra("monkeyId");
        int MonkeyId = Integer.valueOf(fromIntent);
        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);

        MonkeyApi monkeyApi = retrofit.create(MonkeyApi.class);
        Call<Monkey> call = monkeyApi.getMonkey(MonkeyId);
        call.enqueue(new Callback<Monkey>() {
            @Override
            public void onResponse(Call<Monkey> call, Response<Monkey> response) {
                if (response.isSuccessful()) {
                    monkeyDetailNameTextView = findViewById(R.id.monkeyDetailNameTextView);
                    monkeyDetailOwner = findViewById(R.id.monkeyDetailOwner);
                    monkeyDetailImageView = findViewById(R.id.monkeyDetailImageView);
                    monkeyDetailAgeTextView = findViewById(R.id.monkeyDetailAgeTextView);
                    Monkey monkey = response.body();
                    if (monkey.getOwner()==null){
                        monkeyDetailOwner.setText("Cвободна");
                    }
                    else {
                        monkeyDetailOwner.setText("Обезьяна у "+monkey.getOwner().getUsername());
                    }
                    monkeyDetailNameTextView.setText(monkey.getName());
                    monkeyDetailAgeTextView.setText(String.valueOf(monkey.getAge()));
                    Picasso.get().load(monkey.getImage()).into(monkeyDetailImageView);
                }


            }

            @Override
            public void onFailure(Call<Monkey> call, Throwable t) {
                Log.e("Api error", t.getMessage(), t);
            }
        });
    }
}