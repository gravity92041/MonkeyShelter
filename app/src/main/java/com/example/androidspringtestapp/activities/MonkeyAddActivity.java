package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.dto.MonkeyDTO;
import com.example.androidspringtestapp.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonkeyAddActivity extends AppCompatActivity {
    EditText addMonkeyName, addMonkeyAge, addMonkeyImage;
    Button saveMonkey;
String username,role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey_add);
        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        role = intentFrom.getStringExtra("role");
        addMonkeyAge = findViewById(R.id.addMonkeyAge);
        addMonkeyName = findViewById(R.id.addMonkeyName);
        addMonkeyImage = findViewById(R.id.addMonkeyImage);
        saveMonkey = findViewById(R.id.saveMonkeyButton);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MonkeyApi monkeyApi = retrofit.create(MonkeyApi.class);

        saveMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMonkeyName.getText().toString().trim().isEmpty()){
                    addMonkeyName.setError("Имя не должно быть пустым");
                    addMonkeyName.requestFocus();
                }
                if (addMonkeyAge.getText().toString().trim().isEmpty()){
                    addMonkeyAge.setError("Возраст не должен быть пустым");
                    addMonkeyAge.requestFocus();
                }
                if (addMonkeyImage.getText().toString().trim().isEmpty()){
                    addMonkeyImage.setError("Добавьте ссылку на фото");
                    addMonkeyImage.requestFocus();
                }
                else {
                    MonkeyDTO monkeyDTO = new MonkeyDTO(addMonkeyName.getText().toString().trim(),
                            Integer.parseInt(addMonkeyAge.getText().toString().trim()),
                            addMonkeyImage.getText().toString().trim());
                    Call<Void> call = monkeyApi.addMonkey(monkeyDTO);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MonkeyAddActivity.this,"Обезьянка успешно добавлена",Toast.LENGTH_SHORT).show();
//                            setResult(RESULT_OK);
                            Intent intent = new Intent(MonkeyAddActivity.this,MainActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("role",role);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MonkeyAddActivity.this,"Произошла ошибка",Toast.LENGTH_SHORT).show();
                            Log.e("Api error",t.getMessage());
                        }
                    });
                }
            }
        });
    }
}