package com.example.androidspringtestapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.Constants;
import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.util.RetrofitClient;
import com.example.androidspringtestapp.util.TokenManager;
import com.google.android.material.card.MaterialCardView;

import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity {
    MaterialCardView logout,monkeyViewCard,profileCard,adminPeopleViewCard;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        logout = findViewById(R.id.logout);
        adminPeopleViewCard = findViewById(R.id.adminPeopleViewCard);
        monkeyViewCard = findViewById(R.id.monkeyViewCard);
        profileCard = findViewById(R.id.userProfileCard);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");
        String token = intent.getStringExtra("token");
        TokenManager tokenManager = new TokenManager(this);
        tokenManager.saveToken(token);
        Retrofit retrofitClient = RetrofitClient.getClient(Constants.BASE_URL,tokenManager);
        Log.i("role", role);
        if (role.equals("ROLE_ADMIN")||role.equals("ROLE_MAIN")){
            adminPeopleViewCard.setVisibility(View.VISIBLE);
        }
        else {
            adminPeopleViewCard.setVisibility(View.INVISIBLE);
        }
        adminPeopleViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenuActivity.this, UsersViewActivity.class);
                startActivity(intent1);
            }
        });
        monkeyViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("role",role);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}