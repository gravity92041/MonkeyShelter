package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonkeyDetailActivity extends AppCompatActivity {
    private TokenManager tokenManager;
    ImageView monkeyDetailImageView;
    Button takeReleaseMonkey;

    TextView monkeyDetailNameTextView,monkeyDetailOwner,monkeyDetailAgeTextView;
    RelativeLayout adminEditDeleteLL;
    FloatingActionButton editFAB,deleteFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monkey_detail);
        Intent intentFrom = getIntent();
        String username = intentFrom.getStringExtra("username");
        String fromIntent = intentFrom.getStringExtra("monkeyId");
        String role = intentFrom.getStringExtra("role");
        int MonkeyId = Integer.valueOf(fromIntent);
        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);
        adminEditDeleteLL = findViewById(R.id.adminEditDeleteLL);
        editFAB = findViewById(R.id.monkeyEditFAB);
        deleteFAB = findViewById(R.id.monkeyDeleteFAB);
        if (role.equals("ROLE_USER")){
            adminEditDeleteLL.setVisibility(View.INVISIBLE);
            editFAB.hide();
            deleteFAB.hide();
        }
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
                    takeReleaseMonkey = findViewById(R.id.TakeReleaseMonkey);
                    Monkey monkey = response.body();

                    if (monkey.getOwner()==null){
                        monkeyDetailOwner.setText("Cвободна");
                        takeReleaseMonkey.setText("Взять себе");
                        takeReleaseMonkey.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RequestBody requestBody = RequestBody.create(username,MediaType.parse("text/plain"));
                                Call<Void> call2 = monkeyApi.setOwner(monkey.getId(),requestBody);
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(MonkeyDetailActivity.this,"Вы взяли себе обезьянку",Toast.LENGTH_SHORT).show();
                                        MonkeyDetailActivity.this.recreate();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.e("takeReleaseMonkeyCallback",t.getMessage());
                                    }
                                });
                            }
                        });
                    }
                    else if (monkey.getOwner().getUsername().equals(username)){
                        monkeyDetailOwner.setText("Обезьяна у вас");
                        takeReleaseMonkey.setText("Освободить");
                        takeReleaseMonkey.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Call<Void> call1 = monkeyApi.removeOwner(monkey.getId());
                                call1.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(MonkeyDetailActivity.this,"Обезьянка свободна",Toast.LENGTH_SHORT).show();
                                        MonkeyDetailActivity.this.recreate();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.e("takeReleaseMonkeyCallback",t.getMessage());
                                    }
                                });
                            }
                        });
                    }
                    else {
                        monkeyDetailOwner.setText("Обезьяна у пользователя "+monkey.getOwner().getUsername());
                        takeReleaseMonkey.setVisibility(View.INVISIBLE);
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