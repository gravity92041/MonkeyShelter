package com.example.androidspringtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.model.LoginRequest;
import com.example.androidspringtestapp.model.LoginResponse;
import com.example.androidspringtestapp.model.RegistrationRequest;
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText,passwordEditText;
    MonkeyApi monkeyApi;
    private TokenManager tokenManager;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        tokenManager = new TokenManager(this);
//        Retrofit retrofit = RetrofitClient.getClient("http://192.168.100.123:8080",tokenManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.123:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        monkeyApi = retrofit.create(MonkeyApi.class);
        Button loginButton =findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                LoginRequest loginRequest = new LoginRequest(username,password);

                monkeyApi.signIn(loginRequest).enqueue(new Callback<Map<String,String>>() {
                    @Override
                    public void onResponse(Call<Map<String,String>>call, Response<Map<String,String>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            String token = response.body().get("jwt-token");
                            Log.i("Response",response.body().toString());
                            if (token!=null){
                                tokenManager.saveToken(token);
                                Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);

                                intent1.putExtra("username",username);
                                startActivity(intent1);
                            }
                            else {
                                Log.i("Token","MISSING");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String,String>> call, Throwable t) {
                        Log.e("API ERROR",t.getMessage(),t);
                    }
                });
            }
        });
    }
}