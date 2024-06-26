package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.model.RegistrationRequest;
import com.example.androidspringtestapp.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameEditText,passwordEditText,birthYearEditText;
    Button registerButton;
    MonkeyApi monkeyApi;
    TextView loginRedirectText;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameEditText = findViewById(R.id.signup_username);
        passwordEditText = findViewById(R.id.signup_password);
        birthYearEditText = findViewById(R.id.signup_birthYear);
        registerButton=findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        monkeyApi = retrofit.create(MonkeyApi.class);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String birthYear= birthYearEditText.getText().toString().trim();
                if (username.isEmpty()){
                    usernameEditText.setError("Логин не может быть пустым");
                    usernameEditText.requestFocus();
                }
                else if (password.isEmpty()){
                    passwordEditText.setError("Пароль не может быть пустым");
                    passwordEditText.requestFocus();
                }
                else if (birthYear.isEmpty()){
                    birthYearEditText.setError("Год рождения не может быть пустым");
                    birthYearEditText.requestFocus();
                }
                else {
                    RegistrationRequest registrationRequest = new RegistrationRequest(username, password, birthYear);
                    monkeyApi.registerUser(registrationRequest).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("API ERROR", t.getMessage(), t);
                        }
                    });
                }
            }
        });
    }
}