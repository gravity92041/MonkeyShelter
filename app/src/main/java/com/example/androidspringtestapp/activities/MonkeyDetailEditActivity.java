package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.dto.MonkeyFromAndroid;
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MonkeyDetailEditActivity extends AppCompatActivity {
    EditText editMonkeyName,editMonkeyAge;
    Button editMonkeyButton;
    int monkeyId;
    TokenManager tokenManager;
    String name,monkeyAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey_detail_edit);
        Intent intent = getIntent();
        String tempId = intent.getStringExtra("monkeyId");
//        String tempAge = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
//        monkeyAge = Integer.parseInt(tempAge);
        monkeyAge = intent.getStringExtra("age");
        monkeyId = Integer.parseInt(tempId);
        editMonkeyAge = findViewById(R.id.editMonkeyAge);
        editMonkeyName = findViewById(R.id.editMonkeyName);
        editMonkeyButton = findViewById(R.id.editMonkeyButton);
        editMonkeyName.setText(name);
        editMonkeyAge.setText(monkeyAge);
        editMonkeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameChanged()||isAgeChanged()){

                    Retrofit client = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);
                    MonkeyApi monkeyApi = client.create(MonkeyApi.class);
                    MonkeyFromAndroid monkeyFromAndroid = new MonkeyFromAndroid(editMonkeyName.getText().toString().trim(),
                            Integer.valueOf(editMonkeyAge.getText().toString().trim()));
                    Call<Void> call = monkeyApi.updateMonkey(monkeyId,monkeyFromAndroid);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MonkeyDetailEditActivity.this,"Сохранено", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Api error",t.getMessage());
                        }
                    });
                }
                else {
                    Toast.makeText(MonkeyDetailEditActivity.this,"Изменения не найдены",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean isNameChanged(){
        return !name.equals(editMonkeyName.getText().toString().trim());
    }
    public boolean isAgeChanged(){
        return !monkeyAge.equals(editMonkeyAge.getText().toString().trim());
    }
}