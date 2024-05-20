package com.example.androidspringtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidspringtestapp.R;
import com.google.android.material.card.MaterialCardView;

public class AdminMenuActivity extends AppCompatActivity {
    MaterialCardView adminPeopleViewCard,adminMonkeyViewCard,adminProfileCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        adminMonkeyViewCard = findViewById(R.id.adminMonkeyViewCard);
        adminPeopleViewCard = findViewById(R.id.adminPeopleViewCard);
        adminProfileCard = findViewById(R.id.adminProfileCard);
        adminPeopleViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this,UsersViewActivity.class);
                startActivity(intent);
            }
        });
    }
}