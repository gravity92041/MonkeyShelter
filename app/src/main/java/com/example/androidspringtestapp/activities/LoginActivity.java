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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.api.MonkeyApi;
import com.example.androidspringtestapp.model.LoginRequest;
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
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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

                                String role = decodeTokenAndRetrieveRole(token);
                                Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(LoginActivity.this, MenuActivity.class);
                                intent1.putExtra("role",role);
                                intent1.putExtra("username",username);
                                intent1.putExtra("token",token);
                                startActivity(intent1);
                                finish();
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
    public String decodeTokenAndRetrieveRole(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constants.SECRET))
                .withIssuer("brytvich")
                .withSubject("UserDetails")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("role").asString();
    }
}