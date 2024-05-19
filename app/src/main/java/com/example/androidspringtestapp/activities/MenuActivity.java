package com.example.androidspringtestapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.R;
import com.google.android.material.card.MaterialCardView;

public class MenuActivity extends AppCompatActivity {
    MaterialCardView logout,monkeyViewCard,profileCard,adminCard;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        logout = findViewById(R.id.logout);
        adminCard = findViewById(R.id.adminViewCard);
        monkeyViewCard = findViewById(R.id.monkeyViewCard);
        profileCard = findViewById(R.id.profileCard);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");
        Log.i("role", role);
        if (role.equals("ROLE_ADMIN")||role.equals("ROLE_MAIN")){
            adminCard.setVisibility(View.VISIBLE);
        }
        else {
            adminCard.setVisibility(View.INVISIBLE);
        }
        monkeyViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
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